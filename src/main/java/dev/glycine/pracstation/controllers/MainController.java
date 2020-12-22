package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.models.Light;
import io.grpc.StatusRuntimeException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * 程式的主控制器
 */
@Log4j2
public final class MainController {
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane station;
    @FXML
    private Label localTime;
    @FXML
    private Label stationName;
    @FXML
    private HBox topLeftBox;
    @FXML
    private HBox topRightBox;
    @FXML
    private HBox bottomLeftBox;
    @FXML
    private HBox bottomRightBox;

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

    /**
     * 配置縮放指示器
     */
    void configureZoomIndicator() {

    }

    @FXML
    public void initialize() {
        configureToolBox();
        configureCanvas();
        configureClock();
        configureStationNames();
        configureZoomIndicator();
    }

    public void createRoute(MouseEvent mouseEvent) {
        var client = StationController.getInstance().getStationClient();
        var focusedLights = Light.getFocusedLight();
        var list = focusedLights.stream().map(Light::getButtonName).collect(Collectors.toList());
        try {
            client.createRoute(list);
        } catch (StatusRuntimeException e) {
            log.warn(e.getStatus().getDescription());
        }
        Light.defocusAll();
    }
}
