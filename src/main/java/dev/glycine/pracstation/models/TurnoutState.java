package dev.glycine.pracstation.models;

import javafx.scene.paint.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 道岔狀態
 */
@AllArgsConstructor
public enum TurnoutState {
    NORMAL(Color.rgb(48, 209, 88)),
    REVERSE(Color.rgb(255, 214, 10)),
    BROKEN(Color.rgb(255, 69, 58)),
    DISABLED(Color.rgb(58, 58, 60));

    @Getter
    private Color lightColor;
}
