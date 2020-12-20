package dev.glycine.pracstation.components;

import dev.glycine.pracstation.models.Direction;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public abstract class SignalBase extends Group {
    public static final Double DEFAULT_STROKE_WIDTH = 0.75;
    static final Double DEFAULT_CIRCLE_RADIUS = 5.0;
    static final Paint DEFAULT_STROKE_FILL = AppleColor.WHITE;

    @Getter
    @Setter
    String sid;

    @Getter
    SignalType signalType;

    @Getter
    Direction dir;

    public abstract void setDir(Direction dir);
}
