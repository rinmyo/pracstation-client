package dev.glycine.pracstation.models;

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
    NORMAL(Color.rgb(48, 209, 88)),
    /**
     * 反位
     */
    REVERSE(Color.rgb(255, 214, 10)),
    /**
     * 損壞
     */
    BROKEN(Color.rgb(255, 69, 58)),
    /**
     * 禁用
     */
    DISABLED(Color.rgb(58, 58, 60));

    /**
     * 燈光顏色
     */
    @Getter
    private Color lightColor;
}
