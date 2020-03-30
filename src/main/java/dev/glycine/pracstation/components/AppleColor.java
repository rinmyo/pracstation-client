package dev.glycine.pracstation.components;

import javafx.scene.paint.Color;

public enum AppleColor {
    BLUE(Color.rgb(10, 132, 255)),
    GREEN(Color.rgb(48, 209, 88)),
    //todo: 請把main.css中 * 中定義的顏色按照以上兩示例補全


    ;
    private final Color color;

    AppleColor(Color color) {
        this.color = color;
    }
}
