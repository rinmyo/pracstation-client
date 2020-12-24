package dev.glycine.pracstation.models;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ShuntingSignal extends SignalBase {
    @Getter
    Light light1 = new Light(this, "LA");
    @Getter
    Light light2 = new Light(this, "DA");

    Shelter line2 = new Shelter(0, 5, 8.5, 5);

    public ShuntingSignal() {
        signalType = SignalType.SHUNTING_SIGNAL;
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);
        light2.setCenterY(DEFAULT_CIRCLE_RADIUS);
        getChildren().addAll(label, line1, line2, light1, light2);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? 14 : -14);
        light2.setCenterX(dir == Direction.X ? 24 : -24);
        line2.setEndX(dir == Direction.X ? 8.5 : -8.5);
        this.dir = dir;
    }
}
