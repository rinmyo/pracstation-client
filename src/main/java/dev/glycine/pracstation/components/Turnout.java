package dev.glycine.pracstation.components;

import dev.glycine.pracstation.models.TurnoutState;
import dev.glycine.pracstation.models.TurnoutStateProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

public class Turnout extends Group {
    private static final Double DEFAULT_CIRCLE_RADIUS = 5.0;

    @Getter
    @Setter
    String sid;

    @Getter
    Label label = new Label();

    @Getter
    Circle circle = new Circle(DEFAULT_CIRCLE_RADIUS);

    @Getter
    String labelText;

    /**
     * 對應的道岔指示器
     */
    @Getter
    private final TurnoutIndicator indicator;

    /**
     * 道岔狀態
     */
    @Getter
    private final TurnoutStateProperty stateProperty = new TurnoutStateProperty();

    /**
     * 設定道岔狀態
     * @param state 狀態
     */
    public void setState(TurnoutState state) {
        this.stateProperty.set(state);
    }


    public void setLabelText(String labelText) {
        label.getStyleClass().add("turnout-label");
        label.setText(labelText);
    }

    public Turnout() {
        this.indicator = new TurnoutIndicator("" + sid, (e) -> {});

        stateProperty.addListener((observable, oldvalue, newvalue) -> {
            indicator.changeColor(newvalue);
            circle.setFill(newvalue.getColor());
        });

        circle.getStyleClass().add("turnout-circle");
        getChildren().addAll(label, circle);
    }
}
