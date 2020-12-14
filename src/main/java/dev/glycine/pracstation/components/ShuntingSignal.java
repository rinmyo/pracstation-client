package dev.glycine.pracstation.components;

import dev.glycine.pracstation.models.Direction;
import lombok.Getter;

public class ShuntingSignal extends SignalBase {
    @Getter
    Light light1 = new Light();
    @Getter
    Light light2 = new Light();
    Shelter line1 = new Shelter(0, 0, 0, 10);
    Shelter line2 = new Shelter(0, 5, 8.5, 5);

    public ShuntingSignal() {
        signalType = SignalType.SHUNTING_SIGNAL;
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);
        light2.setCenterY(DEFAULT_CIRCLE_RADIUS);
        getChildren().addAll(line1, line2, light1, light2);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? 14 : -14);
        light2.setCenterX(dir == Direction.X ? 24 : -24);
        line2.setEndX(dir == Direction.X ? 8.5 : -8.5);
        this.dir = dir;
    }
}
