package dev.glycine.pracstation.gui;

import com.jfoenix.controls.JFXBadge;
import dev.glycine.pracstation.Turnout;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class TurnoutIndicator extends JFXBadge {
    @Getter
    private Label label = new Label();
    @Getter
    private Turnout turnout;

    @Getter
    private Circle circle = new Circle(10);


    public TurnoutIndicator(String text, Turnout turnout) {
        super();
        label.setText(text);
        this.turnout = turnout;

        label.getStyleClass().add("to-indicator-title");
        circle.getStyleClass().add("to-indicator-circle");
        EventHandler<MouseEvent> clickHandler = e -> {
            turnout.toggleState();
        };
        label.setOnMouseClicked(clickHandler);
        label.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.rgb(28, 28, 30), 5, 0, 0, 0)));
        label.setOnMouseExited(e -> circle.setEffect(null));

        circle.setOnMouseClicked(clickHandler);
        circle.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.rgb(28, 28, 30), 5, 0, 0, 0)));
        circle.setOnMouseExited(e -> circle.setEffect(null));

        getChildren().addAll(circle, label);
    }
}