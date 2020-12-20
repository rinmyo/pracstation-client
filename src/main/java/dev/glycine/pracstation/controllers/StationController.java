package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.components.*;
import dev.glycine.pracstation.components.SectionComponent;
import dev.glycine.pracstation.models.SignalState;
import dev.glycine.pracstation.models.TurnoutState;
import dev.glycine.pracstation.pb.Section;
import dev.glycine.pracstation.pb.Signal;
import dev.glycine.pracstation.service.StationClient;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
public class StationController {
    public AnchorPane station;

    private final HashMap<String, SignalBase> signalMap = new HashMap<>();
    private final HashMap<String, Turnout> turnoutBtnMap = new HashMap<>();
    private final HashMap<String, ArrayList<SectionComponent>> sectionMap = new HashMap<>();

    private final List<SignalBase> focusedSignal = new ArrayList<>();

    public void focus(SignalBase signal) {
        focusedSignal.add(signal);
        signal.getChildren().forEach(e -> {
            if (e instanceof Shape) {
                var shape = (Shape) e;
                shape.setStrokeWidth(1.5);
            }
        });
    }

    public void unfocus(SignalBase signal) {
        focusedSignal.remove(signal);
        signal.getChildren().forEach(e -> {
            if (e instanceof Shape) {
                var shape = (Shape) e;
                shape.setStrokeWidth(SignalBase.DEFAULT_STROKE_WIDTH);
            }
        });
    }

    public void toggleFocused(SignalBase signal) {
        if (focusedSignal.contains(signal)) {
            unfocus(signal);
        } else {
            focus(signal);
        }
    }

    @FXML
    public void initialize() {
        station.getChildren().forEach(e -> {
            if (e instanceof SignalGroup) {
                ((SignalGroup) e).getChildren().forEach(h -> {
                    if (h instanceof SignalBase) {
                        var signal = (SignalBase) h;
                        signalMap.put(signal.getSid(), signal);
                        log.info("initialize signal: " + signal.getSid());
                    }
                });
            }

            if (e instanceof Turnout) {
                var turnout = (Turnout) e;
                turnoutBtnMap.putIfAbsent(turnout.getSid(), turnout);
                log.info("initialize turnout: " + turnout.getSid());
            }

            if (e instanceof SectionComponent) {
                var section = (SectionComponent) e;
                if (sectionMap.containsKey(section.getSid())) {
                    var list = sectionMap.get(section.getSid());
                    list.add(section);
                } else {
                    var list = new ArrayList<SectionComponent>();
                    list.add(section);
                    sectionMap.put(section.getSid(), list);
                }
                log.info("initialize section: " + section.getSid());
            }
        });
        StationClient client = new StationClient("127.0.0.1", 8080);
        client.refreshStation(this);
    }

    public void handleSignalClicked(MouseEvent mouseEvent) {
        var signalBase = (SignalBase) mouseEvent.getSource();
        toggleFocused(signalBase);
    }

    public void updateSignal(Signal pbSignal) {
        var signal = signalMap.get(pbSignal.getId());
        if (signal == null) {
            log.error("unknown signal ID: " + pbSignal.getId());
            return;
        }
        var pbState = pbSignal.getState();
        switch (signal.getSignalType()) {
            case STARTING_SIGNAL -> {
                ((StartingSignal) signal).getLight1().setSignalState(SignalState.fromPbSignalState(pbState));
                ((StartingSignal) signal).getLight1().setSignalState(SignalState.fromPbSignalState(pbSignal.getState()));
                if (pbState == Signal.SignalState.DOUBLE_YELLOW) {
                    ((StartingSignal) signal).getLight2().setSignalState(SignalState.fromPbSignalState(pbSignal.getState()));
                }
            }
            case SHUNTING_SIGNAL -> {
                ((ShuntingSignal) signal).getLight1().setSignalState(SignalState.fromPbSignalState(pbSignal.getState()));
                if (pbState == Signal.SignalState.DOUBLE_YELLOW) {
                    ((ShuntingSignal) signal).getLight2().setSignalState(SignalState.fromPbSignalState(pbSignal.getState()));
                }
            }
            case ROUTE_SIGNAL -> ((RouteSignal) signal).getLight1().setSignalState(SignalState.fromPbSignalState(pbSignal.getState()));
        }
    }

    public void updateTurnout(dev.glycine.pracstation.pb.Turnout pbTurnout) {
        var turnout = turnoutBtnMap.get(pbTurnout.getId());
        if (turnout == null) {
            log.error("unknown turnout id: " + pbTurnout.getId());
        }
        var pbState = pbTurnout.getState();
        var state = TurnoutState.fromPbTurnoutState(pbState);
        turnout.setState(state);
    }

    public void updateSection(Section pbSection) {
        var turnout = turnoutBtnMap.get(pbSection.getId());
        if (turnout == null) {
            log.error("unknown section id: " + pbSection.getId());
        }
    }
}
