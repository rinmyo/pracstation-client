package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.components.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class StationController {
    public ShuntingSignal signalX;
    public ShuntingSignal signalXF;
    public ShuntingSignal signalS;
    public ShuntingSignal signalSF;

    public StartingSignal signalX3;
    public StartingSignal signalXI;
    public StartingSignal signalXII;
    public StartingSignal signalX4;
    public StartingSignal signalX6;
    public StartingSignal signalX8;
    public StartingSignal signalX10;

    public StartingSignal signalS3;
    public StartingSignal signalSI;
    public StartingSignal signalSII;
    public StartingSignal signalS4;
    public StartingSignal signalS6;
    public StartingSignal signalS8;
    public StartingSignal signalS10;

    public RouteSignal signalD1;
    public RouteSignal signalD2;
    public RouteSignal signalD3;
    public RouteSignal signalD4;
    public RouteSignal signalD5;
    public RouteSignal signalD6;
    public RouteSignal signalD7;
    public RouteSignal signalD8;
    public RouteSignal signalD9;
    public RouteSignal signalD10;
    public RouteSignal signalD11;
    public RouteSignal signalD13;
    public RouteSignal signalD15;
    public RouteSignal signalD17;
    public RouteSignal signalD19;
    public RouteSignal signalD21;
    public RouteSignal signalD23;
    public RouteSignal signalD25;
    public RouteSignal signalD27;
    public RouteSignal signalD29;
    public RouteSignal signalD31;
    public RouteSignal signalD33;

    public TurnoutButton turnout1;
    public TurnoutButton turnout3;
    public TurnoutButton turnout5;
    public TurnoutButton turnout7;
    public TurnoutButton turnout9;
    public TurnoutButton turnout11;
    public TurnoutButton turnout13;
    public TurnoutButton turnout15;
    public TurnoutButton turnout17;
    public TurnoutButton turnout19;
    public TurnoutButton turnout21;
    public TurnoutButton turnout23;
    public TurnoutButton turnout25;
    public TurnoutButton turnout27;
    public TurnoutButton turnout29;
    public TurnoutButton turnout31;
    public TurnoutButton turnout33;
    public TurnoutButton turnout35;
    public TurnoutButton turnout37;
    public TurnoutButton turnout2;
    public TurnoutButton turnout4;
    public TurnoutButton turnout6;
    public TurnoutButton turnout8;
    public TurnoutButton turnout10;
    public TurnoutButton turnout12;
    public TurnoutButton turnout14;
    public TurnoutButton turnout16;
    public TurnoutButton turnout18;

    @FXML
    public void initialize() {
        turnout2.getCircle().setOpacity(1);
    }

    public void handleSignalClicked(MouseEvent mouseEvent) {
        var source = (Node) mouseEvent.getSource();
        System.out.println(source.getId());
    }
}
