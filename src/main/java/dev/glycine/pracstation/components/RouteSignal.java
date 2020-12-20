package dev.glycine.pracstation.components;

import dev.glycine.pracstation.models.Direction;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RouteSignal extends SignalBase {
    @Getter
    Light light1 = new Light();
    Shelter line1 = new Shelter(0, 0, 0, 10);

    public RouteSignal() {
        signalType = SignalType.ROUTE_SIGNAL;
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);
        getChildren().addAll(line1, light1);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS);
        this.dir = dir;
    }
}
