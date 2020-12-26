package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.models.ConsoleElement;
import dev.glycine.pracstation.models.InfoState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

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

    public static void writeLn(InfoState state, String str) {
        Platform.runLater(() -> {
            var element = new ConsoleElement(state, str);
            instance.console.getChildren().add(0, element);
        });
    }


}
