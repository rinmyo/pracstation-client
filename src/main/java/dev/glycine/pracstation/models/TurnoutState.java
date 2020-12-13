package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.AppleColor;
import javafx.scene.paint.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
