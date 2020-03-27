package dev.glycine.pracstation;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    private static final HashMap<Integer, Turnout> turnouts = Turnout.getTurnouts();

    public AnchorPane window;
    public StackPane stationGraph;
    public Label localTime;
    public Label stationName;
    public JFXButton newSinro;

    public HBox topRightBox;
    public HBox bottomLeftBox;
    public HBox bottomRightBox;

    private Timeline clock = new Timeline(
            new KeyFrame(Duration.ZERO, e -> localTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
            new KeyFrame(Duration.seconds(1))
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topRightBox.translateXProperty().bind(window.widthProperty().subtract(topRightBox.widthProperty()));
        bottomLeftBox.translateYProperty().bind(window.heightProperty().subtract(bottomLeftBox.heightProperty()));
        bottomRightBox.translateXProperty().bind(window.widthProperty().subtract(bottomRightBox.widthProperty()));
        bottomRightBox.translateYProperty().bind(window.heightProperty().subtract(bottomRightBox.heightProperty()));
        stationGraph.translateXProperty().bind(window.widthProperty().subtract(stationGraph.widthProperty()).divide(2));
        stationGraph.translateYProperty().bind(window.heightProperty().subtract(stationGraph.heightProperty()).divide(2));

        //縮放
        stationGraph.setOnScroll(e -> {
            if (stationGraph.getScaleX() > 1 && stationGraph.getScaleX() < 10 || stationGraph.getScaleX() <= 1 && e.getDeltaY() > 0 || stationGraph.getScaleX() >= 10 && e.getDeltaY() < 0) {
                stationGraph.setScaleX(e.getDeltaY() * 0.001 + stationGraph.getScaleX());
                stationGraph.setScaleY(e.getDeltaY() * 0.001 + stationGraph.getScaleY());
            }
        });

        //平移
        stationGraph.setOnMousePressed(e -> {
            var x = e.getX();
            var y = e.getY();
            stationGraph.setOnMouseDragged(d -> {
                stationGraph.setLayoutX(stationGraph.getLayoutX() + d.getX() - x);
                stationGraph.setLayoutY(stationGraph.getLayoutY() + d.getY() - y);
            });
        });

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        stationName.setText("板橋車站");

        turnouts.get(5).setState(TurnoutState.BROKEN);
        turnouts.get(6).setDoubleActTurnout();
    }
}
