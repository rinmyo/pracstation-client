package dev.glycine.pracstation.models;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
public abstract class SignalBase extends Group {
    static final Double DEFAULT_CIRCLE_RADIUS = 5.0;

    @Getter
    private static final HashMap<String, SignalBase> signals = new HashMap<>();

    public static void register(SignalBase signal) {
        signals.putIfAbsent(signal.sid, signal);
        log.debug("register signal: " + signal.sid);
    }

    public static SignalBase getSignalBySid(String sid) {
        return signals.get(sid);
    }

    @Getter
    String sid;

    public void setSid(String sid) {
        this.sid = sid;
        label.setText(sid);
        label.getStyleClass().add("signal-label");
        register(this);
    }

    @Getter
    SignalType signalType;

    @Getter
    Direction dir;

    Shelter line1 = new Shelter(0, 0, 0, 10);

    @Getter
    Label label = new Label();

    @Getter
    Double labelXOffset;

    public void setLabelXOffset(Double labelXOffset) {
        label.setTranslateX(labelXOffset);
    }

    @Getter
    Double labelYOffset;

    public void setLabelYOffset(Double labelYOffset) {
        label.setTranslateY(labelYOffset);
    }

    @Getter
    Double rotateZ;

    public void setRotateZ(Double rotateZ) {
        Rotate rotate = new Rotate();
        rotate.setAngle(rotateZ);
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        this.getTransforms().add(rotate);
    }

    public abstract void setDir(Direction dir);
}
