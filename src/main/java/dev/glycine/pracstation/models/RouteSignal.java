package dev.glycine.pracstation.models;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RouteSignal extends SignalBase {
    @Getter
    Light light1 = new Light(this, "A");

    public RouteSignal() {
        signalType = SignalType.ROUTE_SIGNAL;
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);
        getChildren().addAll(label, line1, light1);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS);
        this.dir = dir;
    }
}
