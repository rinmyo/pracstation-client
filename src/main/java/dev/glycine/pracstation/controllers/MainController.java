package dev.glycine.pracstation.controllers;

import com.jfoenix.controls.JFXButton;

import dev.glycine.pracstation.models.Turnout;
import dev.glycine.pracstation.models.TurnoutState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * 程式的主控制器
 */
public final class MainController implements Initializable {
    private static final double CANVAS_MIN_SCALE = 1.0;
    private static final double CANVAS_MAX_SCALE = 2.0;
    private static final double ZOOM_TIME = 0.0001;

    private static final HashMap<Integer, Turnout> turnouts = Turnout.getTurnouts();

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane canvas;
    @FXML
    private Label localTime;
    @FXML
    private Label stationName;
    @FXML
    private JFXButton newSinro;
    @FXML
    private HBox topRightBox;
    @FXML
    private HBox bottomLeftBox;
    @FXML
    private HBox bottomRightBox;

    /**
     * 宣告時鐘動畫
     */
    private Timeline clock = new Timeline(
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

    /**
     * 配置畫布
     */
    private void configureCanvas() {
        var scaleProperty = new SimpleDoubleProperty(1.0);

        //將畫布置於窗口的中央
        canvas.translateXProperty().bind(root.widthProperty().subtract(canvas.widthProperty()).divide(2));
        canvas.translateYProperty().bind(root.heightProperty().subtract(canvas.heightProperty()).divide(2));

        //綁定縱橫軸的縮放倍率
        canvas.scaleXProperty().bind(scaleProperty);
        canvas.scaleYProperty().bind(scaleProperty);

        //縮放
        canvas.setOnScroll(e -> {
            var scale = scaleProperty.getValue();
            if (scale > CANVAS_MIN_SCALE && scale < CANVAS_MAX_SCALE
                    || scale <= CANVAS_MIN_SCALE && e.getDeltaY() > 0
                    || scale >= CANVAS_MAX_SCALE && e.getDeltaY() < 0
            ) {
                //todo: zooming relative to the mouse position
                scaleProperty.set(scaleProperty.add(ZOOM_TIME * e.getDeltaY()).getValue());
            }
        });

        //平移
        canvas.setOnMousePressed(e -> {
            var x = e.getX();
            var y = e.getY();
            canvas.setOnMouseDragged(d -> {
                canvas.setCursor(Cursor.MOVE);
                var newX = canvas.getLayoutX() + d.getX() - x;
                var newY = canvas.getLayoutY() + d.getY() - y;
                if (canvas.getScaleX() > CANVAS_MIN_SCALE) {
                    if (Math.abs(newX) <= canvas.getWidth() * (canvas.getScaleX() - CANVAS_MIN_SCALE))
                        canvas.setLayoutX(newX);
                    if (Math.abs(newY) <= canvas.getHeight() * (canvas.getScaleY() - CANVAS_MIN_SCALE))
                        canvas.setLayoutY(newY);
                }
            });
        });
        canvas.setOnMouseReleased(e -> canvas.setCursor(Cursor.DEFAULT));
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
        stationName.setText("板橋車站");
    }

    /**
     * 配置縮放指示器
     */
    void configureZoomIndicator() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureToolBox();
        configureCanvas();
        configureClock();
        configureStationNames();
        configureZoomIndicator();
        turnouts.get(5).setState(TurnoutState.BROKEN);
        turnouts.get(6).setDoubleActTurnout();
    }
}
