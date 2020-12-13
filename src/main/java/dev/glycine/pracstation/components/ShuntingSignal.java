package dev.glycine.pracstation.components;

import dev.glycine.pracstation.Direction;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;

public class ShuntingSignal extends SignalBase {
    @Getter
    Circle light1 = new Circle(DEFAULT_CIRCLE_RADIUS);
    @Getter
    Circle light2 = new Circle(DEFAULT_CIRCLE_RADIUS);
    @Getter
    Line line1 = new Line(0, 0, 0, 10);

    public ShuntingSignal() {
        line1.setStroke(DEFAULT_STROKE_FILL);
        line1.setStrokeWidth(DEFAULT_STROKE_WIDTH);

        light1.setStroke(DEFAULT_STROKE_FILL);
        light1.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);

        light2.setStroke(DEFAULT_STROKE_FILL);
        light2.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        light2.setCenterY(DEFAULT_CIRCLE_RADIUS);

        getChildren().addAll(line1, light1, light2);
    }

    public ShuntingSignal(Direction dir){
        this();
        setDir(dir);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS);
        light2.setCenterX(3 * (dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS));
        this.dir = dir;
    }
}
