package dev.glycine.pracstation.components;

import com.jfoenix.controls.JFXBadge;
import dev.glycine.pracstation.models.SignalState;
import dev.glycine.pracstation.models.Turnout;
import dev.glycine.pracstation.models.TurnoutState;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.TimerTask;

/**
 * 道岔指示器
 */
public class TurnoutIndicator extends JFXBadge {
    @Getter
    private final Label label = new Label();

    @Getter
    private final Circle circle = new Circle(10);

    @Getter
    ProgressIndicator PI = new ProgressIndicator();

    public void changeColor(TurnoutState state){
        circle.setFill(state.getColor());
    }

    public TurnoutIndicator(String text, EventHandler<MouseEvent> clickHandler) {
        label.setText(text);

        label.getStyleClass().add("to-indicator-title");
        circle.getStyleClass().add("to-indicator-circle");

        label.setOnMouseClicked(clickHandler);
        label.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, AppleColor.GRAY_6, 5, 0, 0, 0)));
        label.setOnMouseExited(e -> circle.setEffect(null));

        circle.setOnMouseClicked(clickHandler);
        circle.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, AppleColor.GRAY_6, 5, 0, 0, 0)));
        circle.setOnMouseExited(e -> circle.setEffect(null));

        getChildren().addAll(circle, label, PI);
    }
}