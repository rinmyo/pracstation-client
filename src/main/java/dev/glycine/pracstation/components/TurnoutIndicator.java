package dev.glycine.pracstation.components;

import com.jfoenix.controls.JFXBadge;
import dev.glycine.pracstation.models.Turnout;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import lombok.Getter;

/**
 * 道岔指示器
 */
public class TurnoutIndicator extends JFXBadge {
    @Getter
    private Label label = new Label();
    @Getter
    private Turnout turnout;

    @Getter
    private Circle circle = new Circle(10);

    /**
     * 改變指示器顏色
     */
    public void update() {
        getCircle().setFill(turnout.getState().getLightColor());
    }

    public TurnoutIndicator(String text, Turnout turnout) {
        super();
        label.setText(text);
        this.turnout = turnout;

        label.getStyleClass().add("to-indicator-title");
        circle.getStyleClass().add("to-indicator-circle");

        //道岔表示器點擊處理者
        EventHandler<MouseEvent> clickHandler = e -> turnout.toggleState();

        label.setOnMouseClicked(clickHandler);
        label.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, AppleColor.GRAY_6, 5, 0, 0, 0)));
        label.setOnMouseExited(e -> circle.setEffect(null));

        circle.setOnMouseClicked(clickHandler);
        circle.setOnMouseEntered(e -> circle.setEffect(new InnerShadow(BlurType.GAUSSIAN, AppleColor.GRAY_6, 5, 0, 0, 0)));
        circle.setOnMouseExited(e -> circle.setEffect(null));

        getChildren().addAll(circle, label);
    }
}