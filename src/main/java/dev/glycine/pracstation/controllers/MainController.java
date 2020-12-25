package dev.glycine.pracstation.controllers;

import com.jfoenix.controls.JFXButton;
import dev.glycine.pracstation.models.AppleColor;
import dev.glycine.pracstation.models.Card;
import dev.glycine.pracstation.models.Light;
import dev.glycine.pracstation.models.RouteBadge;
import dev.glycine.pracstation.pb.InitRouteMessage;
import io.grpc.StatusRuntimeException;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 程式的主控制器
 */
@Log4j2
public final class MainController {
    @Getter
    private static MainController instance;

    public MainController() {
        instance = this;
    }

    @FXML
    private FlowPane routePane;
    @FXML
    private JFXButton newRouteBtn;
    @FXML
    private Label focusedBtnLabel;
    @FXML
    private Card routeCard;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane station;
    @FXML
    private Label localTime;
    @FXML
    private Label stationName;
    @FXML
    private HBox topRightBox;
    @FXML
    private HBox bottomLeftBox;
    @FXML
    private HBox bottomRightBox;

    @FXML
    @Getter
    private StationController stationController;
    
    /**
     * 宣告時鐘動畫
     */
    private final Timeline clock = new Timeline(
            new KeyFrame(Duration.ZERO, e -> localTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
            new KeyFrame(Duration.seconds(1))
    );

    /**
     * 配置工具
     */
    private void configureToolBox() {
        topRightBox.translateXProperty().bind(root.widthProperty().subtract(topRightBox.widthProperty()));
        bottomLeftBox.translateYProperty().bind(root.heightProperty().subtract(bottomLeftBox.heightProperty()));
        bottomRightBox.translateXProperty().bind(root.widthProperty().subtract(bottomRightBox.widthProperty()));
        bottomRightBox.translateYProperty().bind(root.heightProperty().subtract(bottomRightBox.heightProperty()));
    }

    private void setDefaultCanvas() {
        //將畫布置於窗口的中央
        station.translateXProperty().bind(root.widthProperty().subtract(station.widthProperty()).divide(2));
        station.translateYProperty().bind(root.heightProperty().subtract(station.heightProperty()).divide(2));

        //綁定縱橫軸的縮放倍率
        station.setScaleX(1.5);
        station.setScaleY(1.5);
    }

    /**
     * 配置畫布
     */
    private void configureCanvas() {
        setDefaultCanvas();
        //縮放
        station.setOnScroll(scrollEvent -> {
            if (scrollEvent.isControlDown()) {
                Scale newScale = new Scale();
                var factor = scrollEvent.getDeltaY() > 0 ? 1.05 : 1 / 1.05;
                newScale.setX(factor);
                newScale.setY(factor);
                newScale.setPivotX(scrollEvent.getX());
                newScale.setPivotY(scrollEvent.getY());
                station.getTransforms().add(newScale);
                scrollEvent.consume();
            }
        });

        //平移
        var mouseXProperty = new SimpleDoubleProperty(0.0);
        var mouseYProperty = new SimpleDoubleProperty(0.0);

        station.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isControlDown()) {
                mouseXProperty.set(mouseEvent.getX());
                mouseYProperty.set(mouseEvent.getY());
                mouseEvent.consume();
            }
        });

        station.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.isControlDown()) {
                station.setCursor(Cursor.MOVE);
                Translate trans = new Translate(
                        mouseEvent.getX() - mouseXProperty.get(),
                        mouseEvent.getY() - mouseYProperty.get()
                );
                station.getTransforms().add(trans);
                mouseEvent.consume();
            }
        });

        station.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.isControlDown()) {
                station.setCursor(Cursor.DEFAULT);
                mouseEvent.consume();
            }
        });
    }

    /**
     * 配置時鐘
     */
    void configureClock() {
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * 配置站名
     */
    void configureStationNames() {
        stationName.setText("臨灃站");
    }

    @FXML
    public void initialize() {
        configureToolBox();
        configureCanvas();
        configureClock();
        configureStationNames();

        var icon = new FontIcon(FontAwesomeSolid.PLUS);
        icon.setIconSize(20);
        icon.setIconColor(AppleColor.GRAY_6);
        newRouteBtn.setPadding(new Insets(5));
        newRouteBtn.setGraphic(icon);
        ConsoleController.writeLn(ConsoleController.InfoState.INFO, "基本視圖初始化完成");
    }

    public void handleCreateRoute() {
        var client = stationController.getStationClient();
        var focusedLights = Light.getFocusedLight();
        var list = focusedLights.stream().map(Light::getButtonName).collect(Collectors.toList());
        var protectedLight = focusedLights.get(0);
        new Thread(() -> {
            try {
                var response = client.createRoute(list);
                addToRoutePane(response.getRouteId(), protectedLight);
                ConsoleController.writeLn(ConsoleController.InfoState.SUCCESS, "建立進路成功: " + response.getRouteId());
            } catch (StatusRuntimeException e) {
                log.warn(e.getStatus().getDescription());
                ConsoleController.writeLn(ConsoleController.InfoState.WARN, "建立進路失敗: " + e.getStatus().getDescription());
            }
        }).start();
        Light.defocusAll();
        updateNewRouteBtn(Light.getFocusedLight());

    }

    public void handleCancelRoute(MouseEvent mouseEvent) {
        var route = (RouteBadge) mouseEvent.getSource();
        var client = stationController.getStationClient();
        new Thread(() -> {
            try {
                client.cancelRoute(route.getRouteId());
                removeFromRoutePane(route.getRouteId());
                ConsoleController.writeLn(ConsoleController.InfoState.SUCCESS, "取消進路成功: " + route.getRouteId());
            } catch (StatusRuntimeException e) {
                log.warn(e.getStatus().getDescription());
                ConsoleController.writeLn(ConsoleController.InfoState.WARN, "取消進路失敗: " + e.getStatus().getDescription());
            }
        }).start();
    }

    public void addToRoutePane(String s, Light light) {
        Platform.runLater(() -> {
            var badge = new RouteBadge(s, light);
            var fade = new FadeTransition(Duration.seconds(0.5), badge);
            fade.setFromValue(0);
            fade.setToValue(1);
            routePane.getChildren().add(badge);
            routeCard.resize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            fade.play();
        });
    }

    public void removeFromRoutePane(String s) {
        Platform.runLater(() -> {
            routePane.getChildren().removeAll(routePane.getChildren().filtered(e -> ((RouteBadge) e).getRouteId().equals(s)));
            routeCard.resize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        });
    }

    public void initRoutePane(List<InitRouteMessage> res) {
        Platform.runLater(() -> {
            routePane.getChildren().clear();
            res.forEach(e -> {
                addToRoutePane(e.getRouteId(), Light.getLightByButtonName(e.getButtonId()));
                log.debug(2 + e.getButtonId() + ": " + Light.getLightByButtonName(e.getButtonId()));
            });
        });
    }

    public void updateNewRouteBtn(List<Light> focusedLight) {
        var str = focusedLight.stream().map(Light::getButtonName).collect(Collectors.joining(", "));
        focusedBtnLabel.setText(str);
        var box = (Card) focusedBtnLabel.getParent().getParent();
        box.resize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

}
