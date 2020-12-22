package dev.glycine.pracstation.models;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Shelter extends Line {
    static final Double DEFAULT_STROKE_WIDTH = 0.75;
    static final Paint DEFAULT_STROKE_FILL = AppleColor.WHITE;

    public Shelter(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        setStroke(DEFAULT_STROKE_FILL);
        setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }
}
