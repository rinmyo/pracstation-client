package dev.glycine.pracstation.components;

import dev.glycine.pracstation.models.TurnoutState;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import lombok.Getter;

/**
 * 道岔指示器
 */
public class TurnoutIndicator extends StackPane {
    @Getter
    private final Label label = new Label();

    @Getter
    private final Circle circle = new Circle(10);

    @Getter
    Spinner spinner = new Spinner();

    public void changeColor(TurnoutState state){
        circle.setFill(state.getColor());
    }

    public TurnoutIndicator(String text, EventHandler<MouseEvent> clickHandler) {
        label.setText(text);
        label.setOnMouseClicked(clickHandler);
        label.getStyleClass().add("to-indicator-title");
        label.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, AppleColor.GRAY_6, 5, 0, 0, 0)));
        label.setOnMouseExited(e -> circle.setEffect(null));

        circle.setOnMouseClicked(clickHandler);
        circle.getStyleClass().add("to-indicator-circle");
        circle.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, AppleColor.GRAY_6, 5, 0, 0, 0)));
        circle.setOnMouseExited(e -> circle.setEffect(null));

        getChildren().addAll(circle, label);
    }
}