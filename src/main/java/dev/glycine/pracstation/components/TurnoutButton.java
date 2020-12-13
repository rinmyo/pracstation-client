package dev.glycine.pracstation.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class TurnoutButton extends Group {
    private static final Double DEFAULT_CIRCLE_RADIUS = 5.0;

    @Getter
    Label label = new Label();

    @Getter
    Circle circle = new Circle(DEFAULT_CIRCLE_RADIUS);

    @Getter
    String labelText;

    public void setLabelText(String labelText) {
        label.getStyleClass().add("turnout-label");
        label.setText(labelText);
    }

    public TurnoutButton() {
        circle.getStyleClass().add("turnout-circle");
        getChildren().addAll(label, circle);
    }
}
