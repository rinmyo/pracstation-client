package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.models.*;
import dev.glycine.pracstation.models.Section;
import dev.glycine.pracstation.models.SignalState;
import dev.glycine.pracstation.models.TurnoutState;
import dev.glycine.pracstation.pb.Signal;
import dev.glycine.pracstation.service.StationClient;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class StationController {
    public AnchorPane station;
    @Getter
    private final StationClient stationClient;

    @Getter
    private static final StationController instance = new StationController();

    StationController() {
        log.debug("start init");
        stationClient = new StationClient("127.0.0.1", 8080);
        new Thread(() -> {
            stationClient.refreshStation(this);
        }).start();
    }

    public void updateSignal(Signal pbSignal) {
        var signal = SignalBase.getSignalBySid(pbSignal.getId());
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
        var turnout = Turnout.getTurnoutBySid(pbTurnout.getId());
        if (turnout == null) {
            log.error("unknown turnout id: " + pbTurnout.getId());
            return;
        }
        var pbState = pbTurnout.getState();
        var state = TurnoutState.fromPbTurnoutState(pbState);
        turnout.setState(state);
        //所在的軌道區段
        var sections = Section.getSectionsBySid(turnout.getSid());
        if (sections == null) {
            log.error("unknown section id: " + turnout.getSid());
            return;
        }
        var secOp = sections.stream().filter(s -> s.getDependencies().isEmpty()).findFirst();
        if (secOp.isPresent()) {
            var secState = secOp.get().getState();
            if (secState == SectionState.FREE) {
                sections.forEach(e -> e.setState(secState));
            } else {
                sections.stream().filter(e -> e.getDependencies().get(turnout.getTid()) == state).forEach(e -> e.setState(secState));
            }
        }
    }

    public void updateSection(dev.glycine.pracstation.pb.Section pbSection) {
        var section = Section.getSectionsBySid(pbSection.getId());
        if (section == null) {
            log.error("unknown section id: " + pbSection.getId());
            return;
        }
        var pbState = pbSection.getState();
        var state = SectionState.fromPbSectionState(pbState);
        section.forEach(e -> {
            var deps = e.getDependencies();
            AtomicBoolean flag = new AtomicBoolean(true);
            deps.forEach((k, v) -> {
                if (state == SectionState.LOCKED) {
                    if (Turnout.getTurnoutBySid(k).getState() != v) flag.set(false);
                }
            });
            if (flag.get()) e.setState(state);
            log.debug("set section id: " + e.getSid() + "： " + state);
        });
    }
}
