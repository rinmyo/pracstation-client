package dev.glycine.pracstation.models;

import dev.glycine.pracstation.components.AppleColor;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectionState {

    OCCUPIED(AppleColor.RED),

    FREE(AppleColor.TEAL);

    /**
     * 燈光顏色
     */
    @Getter
    private final Color color;
}
