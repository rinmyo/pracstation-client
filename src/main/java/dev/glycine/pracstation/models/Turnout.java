package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.TurnoutIndicator;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 道岔類
 */
@Log4j2
public class Turnout {
    private static final TurnoutState DEFAULT_STATE = TurnoutState.UNKNOWN;

    private Turnout(int tid, Rectangle normalRec, Rectangle reverseRec) {
        this.tid = tid;
        this.normalRec = normalRec;
        this.reverseRec = reverseRec;

        this.doubleActTurnout = null;
        this.indicator = new TurnoutIndicator("" + tid, e -> toggleState());

        stateProperty.addListener((observable, oldvalue, newvalue) -> {
            indicator.changeColor(newvalue);
            switch (newvalue){
                case NORMAL -> {
//                reverseRec.setVisible(false);
//                normalRec.setVisible(true);
                }
                case REVERSE -> {
//                reverseRec.setVisible(true);
//                normalRec.setVisible(false);
                }
            }

            if (isDoubleActTurnout()) {
                doubleActTurnout.setState(newvalue);
            }
        });

        setState(DEFAULT_STATE);
    }

    /**
     * 所有道岔
     */
    @Getter
    private static final TurnoutMap turnouts = new TurnoutMap();

    @Getter
    private static int MAX_ODD, MAX_EVEN;

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
    private final int tid;

    /**
     * 對應的道岔指示器
     */
    @Getter
    private final TurnoutIndicator indicator;

    @Getter
    private final Rectangle normalRec, reverseRec;

    /**
     * 道岔狀態
     */
    @Getter
    private final TurnoutStateProperty stateProperty = new TurnoutStateProperty();

    /**
     * 設定道岔狀態
     * @param state 狀態
     */
    public void setState(TurnoutState state) {
        this.stateProperty.set(state);
    }

    /**
     * 反轉道岔
     */
    public void toggleState() {
        if (stateProperty.get() == TurnoutState.NORMAL) {
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

    public static void setTurnouts(int odd, int even) {
        MAX_EVEN = even;
        MAX_ODD = odd;

        for (int i = 1; i <= odd; i+=2) {
            turnouts.put(i, new Turnout(i, null, null));
        }
        for (int i = 2; i <= even; i+=2) {
            turnouts.put(i, new Turnout(i, null, null));
        }
    }

    public boolean isDoubleActTurnout() {
        return doubleActTurnout != null;
    }
}
