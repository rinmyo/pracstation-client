package dev.glycine.pracstation.models;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public enum InfoState {
    WARN("-fx-orange", FontAwesomeSolid.EXCLAMATION_CIRCLE),
    INFO("-fx-gray-2", FontAwesomeSolid.INFO_CIRCLE),
    ERROR("-fx-red", FontAwesomeSolid.TIMES_CIRCLE),
    SUCCESS("-fx-green", FontAwesomeSolid.CHECK_CIRCLE);

    String color;
    FontAwesomeSolid fontIcon;

    InfoState(String color, FontAwesomeSolid fontIcon) {
        this.color = color;
        this.fontIcon = fontIcon;
    }
}