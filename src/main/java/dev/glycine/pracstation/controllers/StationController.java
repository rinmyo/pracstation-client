package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.components.StartingSignal;
import javafx.fxml.FXML;
import javafx.scene.Group;

public class StationController {
    public StartingSignal signalX4;

    @FXML
    private Group signalD27;
    @FXML
    private Group signalD3;

    @FXML
    public void initialize() {
        System.out.println("hello");
    }
}
