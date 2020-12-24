package dev.glycine.pracstation.models;

import dev.glycine.pracstation.exceptions.UnknownSignalStateException;
import dev.glycine.pracstation.pb.Signal;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SignalState {
    UNKNOWN(AppleColor.GRAY),

    BROKEN(AppleColor.BLACK),

    BLACK(AppleColor.BLACK),

    RED(AppleColor.RED),

    YELLOW(AppleColor.YELLOW),

    DOUBLE_YELLOW(AppleColor.YELLOW),

    GREEN(AppleColor.GREEN),

    BLUE(AppleColor.BLUE),

    WHITE(AppleColor.WHITE);

    /**
     * 燈光顏色
     */
    @Getter
    private final Color color;

    public static SignalState fromPbSignalState(Signal.SignalState pbSignalState) throws UnknownSignalStateException {
        return switch (pbSignalState){
            case UNKNOWN -> UNKNOWN;
            case BROKEN -> BROKEN;
            case BLACK -> BLACK;
            case RED -> RED;
            case YELLOW -> YELLOW;
            case DOUBLE_YELLOW -> DOUBLE_YELLOW;
            case GREEN -> GREEN;
            case BLUE -> BLUE;
            case WHITE -> WHITE;
            case UNRECOGNIZED -> throw new UnknownSignalStateException();
        };
    }
}
