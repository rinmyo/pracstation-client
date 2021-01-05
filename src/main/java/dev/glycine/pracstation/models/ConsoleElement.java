package dev.glycine.pracstation.models;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleElement extends HBox {

    public ConsoleElement(InfoState state, String str) {
        var time = new Label(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        time.getStyleClass().add("-time-label");
        var content = new Label(str);
        getChildren().addAll(time, content);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
        var icon = new FontIcon(state.fontIcon);
        icon.setIconColor(AppleColor.WHITE);
        icon.setIconSize(11);
        time.setGraphic(icon);
        time.setStyle("-fx-background-color: " + state.color);
        content.setStyle("-fx-text-fill: white;");
    }
}
