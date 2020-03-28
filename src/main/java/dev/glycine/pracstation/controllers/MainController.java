package dev.glycine.pracstation.controllers;

import com.jfoenix.controls.JFXButton;

import dev.glycine.pracstation.Turnout;
import dev.glycine.pracstation.TurnoutState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class MainController implements Initializable {
    private static final double CANVAS_MIN_SCALE = 1.0;
    private static final double CANVAS_MAX_SCALE = 2.0;
    private static final double ZOOM_TIME = 0.0001;

    private static final HashMap<Integer, Turnout> turnouts = Turnout.getTurnouts();

    public AnchorPane window;
    public AnchorPane canvas;
    public Label localTime;
    public Label stationName;
    public JFXButton newSinro;

    public HBox topRightBox;
    public HBox bottomLeftBox;
    public HBox bottomRightBox;
    public JFXButton zoomIndicator;

    private Timeline clock = new Timeline(
            new KeyFrame(Duration.ZERO, e -> localTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
            new KeyFrame(Duration.seconds(1))
    );

    private void configureTools() {
        topRightBox.translateXProperty().bind(window.widthProperty().subtract(topRightBox.widthProperty()));
        bottomLeftBox.translateYProperty().bind(window.heightProperty().subtract(bottomLeftBox.heightProperty()));
        bottomRightBox.translateXProperty().bind(window.widthProperty().subtract(bottomRightBox.widthProperty()));
        bottomRightBox.translateYProperty().bind(window.heightProperty().subtract(bottomRightBox.heightProperty()));
    }


    private void configureStationGraph() {
        var scaleProperty = new SimpleDoubleProperty(1.0);

        canvas.translateXProperty().bind(window.widthProperty().subtract(canvas.widthProperty()).divide(2));
        canvas.translateYProperty().bind(window.heightProperty().subtract(canvas.heightProperty()).divide(2));
        canvas.scaleXProperty().bind(scaleProperty);
        canvas.scaleYProperty().bind(scaleProperty);

        canvas.setOnScrollStarted(e -> {
            System.out.println(1);
        });

        //縮放
        SimpleDoubleProperty cursorX = new SimpleDoubleProperty(0);
        SimpleDoubleProperty cursorY = new SimpleDoubleProperty(0);
        canvas.setOnScroll(e -> {
            var scale = scaleProperty.getValue();
            if (scale > CANVAS_MIN_SCALE && scale < CANVAS_MAX_SCALE
                    || scale <= CANVAS_MIN_SCALE && e.getDeltaY() > 0
                    || scale >= CANVAS_MAX_SCALE && e.getDeltaY() < 0
            ) {
                //todo: program will occur a bug here, cannot work as normal
                canvas.setLayoutX(canvas.getLayoutX() - e.getX() - cursorX.getValue());
                canvas.setLayoutY(canvas.getLayoutY() - e.getY() - cursorY.getValue());
                scaleProperty.set(scaleProperty.add(ZOOM_TIME * e.getDeltaY()).getValue());
                cursorX.set(e.getX());
                cursorY.set(e.getY());
            }
        });

        //平移
        canvas.setOnMousePressed(e -> {
            var x = e.getX();
            var y = e.getY();
            canvas.setOnMouseDragged(d -> {
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
    }

    void configureClock() {
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    void configureStationNames() {
        stationName.setText("板橋車站");
    }

    void configureZoomIndicator() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTools();
        configureStationGraph();
        configureClock();
        configureStationNames();
        configureZoomIndicator();
        turnouts.get(5).setState(TurnoutState.BROKEN);
        turnouts.get(6).setDoubleActTurnout();
    }
}
