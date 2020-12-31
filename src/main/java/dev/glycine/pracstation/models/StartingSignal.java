package dev.glycine.pracstation.models;

import lombok.Getter;

public class StartingSignal extends SignalBase {
    @Getter
    Light light1 = new Light(this, "LA");
    @Getter
    Light light2 = new Light(this, "DA");


    public StartingSignal() {
        signalType = SignalType.STARTING_SIGNAL;
        light1.setCenterY(DEFAULT_CIRCLE_RADIUS);
        light2.setCenterY(DEFAULT_CIRCLE_RADIUS);
        getChildren().addAll(label, line1, light1, light2);
    }

    public void setDir(Direction dir) {
        light1.setCenterX(dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS);
        light2.setCenterX(3 * (dir == Direction.X ? DEFAULT_CIRCLE_RADIUS : -DEFAULT_CIRCLE_RADIUS));
        this.dir = dir;
    }
}
