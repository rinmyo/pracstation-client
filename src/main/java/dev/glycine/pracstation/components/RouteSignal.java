package dev.glycine.pracstation.components;

import dev.glycine.pracstation.Direction;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;

public class RouteSignal extends SignalBase {
    @Getter
    Circle light1 = new Circle(DEFAULT_CIRCLE_RADIUS);
    @Getter
    Line line1 = new Line(0, 0, 0, 10);

    public RouteSignal() {
        line1.setStroke(DEFAULT_STROKE_FILL);
        line1.setStrokeWidth(DEFAULT_STROKE_WIDTH);

        light1.setStroke(DEFAULT_STROKE_FILL);
        light1.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);

        getChildren().addAll(line1, light1);
    }

    public RouteSignal(Direction dir){
        this();
        setDir(dir);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS);
        this.dir = dir;
    }
}
