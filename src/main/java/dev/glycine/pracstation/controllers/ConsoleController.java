package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.models.AppleColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
public class ConsoleController {
    @Getter
    public static ConsoleController instance;

    public ConsoleController() {
        log.debug("init console");
        instance = this;
    }

    @FXML
    private VBox console;

    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");// 格式化时间

    public static void writeLn(InfoState state, String str) {
        Platform.runLater(() -> {
            var date = new Date();// 获取当前时间
            var time = new Label(sdf.format(date));
            time.getStyleClass().add("-time-label");
            var content = new Label(str);
            var box = new HBox(time, content);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setSpacing(5);
            var icon = new FontIcon(state.fontIcon);
            icon.setIconColor(AppleColor.WHITE);
            icon.setIconSize(12);
            time.setGraphic(icon);
            time.setStyle("-fx-background-color: " + state.color);
            content.setStyle("-fx-text-fill: white;");
            instance.console.getChildren().add(0, box);
        });
    }

    public enum InfoState {
        WARN("-fx-orange", FontAwesomeSolid.QUESTION_CIRCLE),
        INFO("-fx-gray-2", FontAwesomeSolid.INFO_CIRCLE),
        ERROR("-fx-red", FontAwesomeSolid.EXCLAMATION_CIRCLE),
        SUCCESS("-fx-green", FontAwesomeSolid.CHECK_CIRCLE);

        String color;
        FontAwesomeSolid fontIcon;

        InfoState(String color, FontAwesomeSolid fontIcon) {
            this.color = color;
            this.fontIcon = fontIcon;
        }
    }
}
