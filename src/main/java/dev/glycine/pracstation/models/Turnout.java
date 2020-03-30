package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.TurnoutIndicator;
import lombok.Getter;

import java.util.HashMap;

public class Turnout {
    /**
     * 所有道岔
     */
    @Getter
    private static final HashMap<Integer, Turnout> turnouts = new HashMap<>();

    public static Turnout getTurnoutById(int id) {
        return turnouts.get(id);
    }


    /**
     * 道岔編號
     */
    @Getter
    private int tid;

    @Getter
    private TurnoutIndicator indicator;

    /**
     * 道岔狀態
     */
    @Getter
    private TurnoutState state;

    public void setState(TurnoutState state) {
        this.state = state;
        indicator.getCircle().setFill(state.getLightColor());

        if (isDoubleActTurnout()) {
            doubleActTurnout.indicator.getCircle().setFill(state.getLightColor());
        }
    }

    public void toggleState() {
        if (this.state == TurnoutState.NORMAL) {
            setState(TurnoutState.REVERSE);
        } else {
            setState(TurnoutState.NORMAL);
        }
    }

    /**
     * 雙動道岔
     */
    @Getter
    private Turnout doubleActTurnout;

    public void setDoubleActTurnout() {
        this.doubleActTurnout = getTurnoutById(tid + 2);
        getTurnoutById(tid + 2).doubleActTurnout = this;
    }

    private Turnout(int tid) {
        this.tid = tid;
        this.doubleActTurnout = null;
        this.indicator = new TurnoutIndicator("" + tid, this);
        setState(TurnoutState.NORMAL);
    }

    public static void setTurnouts(int amount) {
        for (int i = 1; i <= amount; i++) {
            turnouts.put(i, new Turnout(i));
        }
    }

    public boolean isDoubleActTurnout() {
        return doubleActTurnout != null;
    }
}
