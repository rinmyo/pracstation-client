package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.AppleColor;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SignalState {

    RED(AppleColor.RED),

    YELLOW(AppleColor.YELLOW),

    GREEN(AppleColor.GREEN),

    BLUE(AppleColor.BLUE),

    WHITE(AppleColor.WHITE),

    BLACK(AppleColor.BLACK);

    /**
     * 燈光顏色
     */
    @Getter
    private final Color color;
}
