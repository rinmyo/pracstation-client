package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.AppleColor;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SignalState {
    UNKNOWN(AppleColor.GRAY),

    OFF(AppleColor.BLACK),

    STOP(AppleColor.RED),

    CAUTION(AppleColor.YELLOW),

    PROCEED(AppleColor.GREEN),

    ROUTE_STOP(AppleColor.BLUE),

    ROUTE_PROCEED(AppleColor.WHITE);

    /**
     * 燈光顏色
     */
    @Getter
    private final Color color;
}
