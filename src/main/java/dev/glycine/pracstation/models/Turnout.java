package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.TurnoutIndicator;
import lombok.Getter;

import java.util.HashMap;

/**
 * 道岔類
 */
public class Turnout {
    /**
     * 所有道岔
     */
    @Getter
    private static final HashMap<Integer, Turnout> turnouts = new HashMap<>();

    /**
     * 通過tid獲得道岔物件
     * @param tid 道岔編號
     * @return 道岔物件
     */
    public static Turnout getTurnoutById(int tid) {
        return turnouts.get(tid);
    }

    /**
     * 道岔編號
     */
    @Getter
    private int tid;

    /**
     * 對應的道岔指示器
     */
    @Getter
    private TurnoutIndicator indicator;

    /**
     * 道岔狀態
     */
    @Getter
    private TurnoutState state;

    /**
     * 設定道岔狀態
     * @param state 狀態
     */
    public void setState(TurnoutState state) {
        this.state = state;
        indicator.update();

        if (isDoubleActTurnout()) {
            doubleActTurnout.state = state;
            doubleActTurnout.indicator.update();
        }
    }

    /**
     * 反轉道岔
     */
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

    /**
     * 設置雙動道岔
     */
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
