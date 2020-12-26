package dev.glycine.pracstation.models;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleElement extends HBox {

    public ConsoleElement(InfoState state, String str) {
        var time = new Label(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        time.getStyleClass().add("-time-label");
        var content = new Label(str);
        getChildren().addAll(time, content);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
        var icon = new FontIcon(state.fontIcon);
        icon.setIconColor(AppleColor.WHITE);
        icon.setIconSize(12);
        time.setGraphic(icon);
        time.setStyle("-fx-background-color: " + state.color);
        content.setStyle("-fx-text-fill: white;");
    }
}
