package dev.glycine.pracstation.models;

import dev.glycine.pracstation.exceptions.UnknownSignalStateException;
import dev.glycine.pracstation.exceptions.UnknownTurnoutStateException;
import dev.glycine.pracstation.pb.Turnout;
import dev.glycine.pracstation.components.AppleColor;
import javafx.scene.paint.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * 道岔狀態
 */
@AllArgsConstructor
public enum TurnoutState {
    /**
     * 定位
     */
    NORMAL(AppleColor.GREEN),
    /**
     * 反位
     */
    REVERSE(AppleColor.YELLOW),
    /**
     * 損壞
     */
    BROKEN(AppleColor.RED),
    /**
     * 未知
     */
    UNKNOWN(AppleColor.GRAY_4);

    /**
     * 燈光顏色
     */
    @Getter
    private final Color color;

    public static TurnoutState fromPbTurnoutState(@NonNull Turnout.TurnoutState pbTurnoutState){
        return switch (pbTurnoutState) {
            case UNKNOWN -> UNKNOWN;
            case BROKEN -> BROKEN;
            case NORMAL -> NORMAL;
            case REVERSED -> REVERSE;
            case UNRECOGNIZED -> throw new UnknownTurnoutStateException();
        };
    }
}
