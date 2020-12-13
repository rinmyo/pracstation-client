package dev.glycine.pracstation.controllers;

import com.jfoenix.controls.JFXButton;

import dev.glycine.pracstation.models.Turnout;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * 程式的主控制器
 */
public final class MainController {
    private static final double CANVAS_MIN_SCALE = 0.5;
    private static final double CANVAS_MAX_SCALE = 5.0;

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

    /**
     * 配置畫布
     */
    private void configureCanvas() {
        //將畫布置於窗口的中央
        canvas.translateXProperty().bind(root.widthProperty().subtract(canvas.widthProperty()).divide(2));
        canvas.translateYProperty().bind(root.heightProperty().subtract(canvas.heightProperty()).divide(2));

        //綁定縱橫軸的縮放倍率
        canvas.setScaleX(1.5);
        canvas.setScaleY(1.5);

        //縮放
        canvas.setOnScroll(scrollEvent -> {
            Scale newScale = new Scale();
            var factor = scrollEvent.getDeltaY() > 0 ? 1.05 : 1 / 1.05;
            newScale.setX(factor);
            newScale.setY(factor);
            newScale.setPivotX(scrollEvent.getX());
            newScale.setPivotY(scrollEvent.getY());
            canvas.getTransforms().add(newScale);
            scrollEvent.consume();
        });

        //平移
        var mouseXProperty = new SimpleDoubleProperty(0.0);
        var mouseYProperty = new SimpleDoubleProperty(0.0);

        canvas.setOnMousePressed(mouseEvent -> {
            mouseXProperty.set(mouseEvent.getX());
            mouseYProperty.set(mouseEvent.getY());
            mouseEvent.consume();
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            canvas.setCursor(Cursor.MOVE);
            Translate trans = new Translate(
                    mouseEvent.getX() - mouseXProperty.get(),
                    mouseEvent.getY() - mouseYProperty.get()
            );
            canvas.getTransforms().add(trans);
            mouseEvent.consume();
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            canvas.setCursor(Cursor.DEFAULT);
            mouseEvent.consume();
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
}
