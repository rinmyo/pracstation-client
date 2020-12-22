package dev.glycine.pracstation.models;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;

@Log4j2
public class Turnout extends Group {
    private static final Double DEFAULT_CIRCLE_RADIUS = 5.0;

    @Getter
    private static final HashMap<String, Turnout> turnouts = new HashMap<>();

    public static void register(Turnout turnout) {
        turnouts.putIfAbsent(turnout.tid, turnout);
        log.debug("register turnout: " + turnout.tid);
    }

    public static Turnout getTurnoutBySid(String sid) {
        return turnouts.get(sid);
    }

    @Getter
    String tid;

    public void setTid(String tid) {
        this.tid = tid;
        label.setText(tid);
        label.getStyleClass().add("turnout-label");
        register(this);
    }

    @Getter
    @Setter
    String sid;

    @Getter
    Label label = new Label();

    @Getter
    Circle circle;

    /**
     * 道岔狀態
     */
    private final TurnoutStateProperty stateProperty = new TurnoutStateProperty();

    /**
     * 設定道岔狀態
     *
     * @param state 狀態
     */
    public void setState(TurnoutState state) {
        log.debug("set the turnout " + this.getTid() + " to: " + state);
        this.stateProperty.set(state);
    }

    public TurnoutState getState() {
        return stateProperty.get();
    }

    public Turnout() {
        circle = new Circle(DEFAULT_CIRCLE_RADIUS);
        setOnMouseClicked(e -> setState(TurnoutState.NORMAL));
        stateProperty.addListener((observable, oldvalue, newvalue) -> {
            circle.setFill(newvalue.getColor());
        });

        circle.getStyleClass().add("turnout-circle");
        getChildren().addAll(label, circle);
    }
}
