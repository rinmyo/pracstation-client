package dev.glycine.pracstation.components;

import dev.glycine.pracstation.models.SignalState;
import dev.glycine.pracstation.models.SignalStateProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class Light extends Circle {
    private static final Double DEFAULT_STROKE_WIDTH = 0.75;
    private static final Double DEFAULT_CIRCLE_RADIUS = 5.0;
    private static final Paint DEFAULT_STROKE_FILL = AppleColor.WHITE;
    private static final SignalState DEFAULT_SIGNAL_STATE = SignalState.UNKNOWN;

    Light(){
        super(DEFAULT_CIRCLE_RADIUS);
        setStroke(DEFAULT_STROKE_FILL);
        setStrokeWidth(DEFAULT_STROKE_WIDTH);
        setSignalState(DEFAULT_SIGNAL_STATE);
        signalStateProperty.addListener((observable, oldvalue, newvalue) -> setFill(newvalue.getColor()));
    }

    @Getter
    private final SignalStateProperty signalStateProperty = new SignalStateProperty();

    public void setSignalState(SignalState signalState) {
        this.signalStateProperty.set(signalState);
    }
}
