package dev.glycine.pracstation.service;

import com.google.protobuf.Empty;
import dev.glycine.pracstation.controllers.MainController;
import dev.glycine.pracstation.controllers.StationController;
import dev.glycine.pracstation.pb.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class StationClient {
    private final ManagedChannel channel;
    private final StationServiceGrpc.StationServiceBlockingStub blockingStub;

    public StationClient(String host, int port, Token token) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveWithoutCalls(true)
                .usePlaintext()
                .build();

        blockingStub = StationServiceGrpc.newBlockingStub(channel).withCallCredentials(token);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void initStation(StationController controller) {
        log.debug("initialize station states");
        InitStationResponse response;
        try {
            response = blockingStub.initStation(Empty.getDefaultInstance());
        } catch (StatusRuntimeException e) {
            log.error(e.getStatus().getDescription());
            return;
        }
        response.getTurnoutList().forEach(e -> log.debug("get turnout: " + e.getId() + ": " + e.getState()));
        response.getSectionList().forEach(e -> log.debug("get section: " + e.getId() + ": " + e.getState()));
        response.getSignalList().forEach(e -> log.debug("get signal: " + e.getId() + ": " + e.getState()));
        response.getRouteIdList().forEach(e -> log.debug("get route: " + e));

        response.getTurnoutList().forEach(controller::updateTurnout);
        response.getSectionList().forEach(controller::updateSection);
        response.getSignalList().forEach(controller::updateSignal);
        MainController.getInstance().initRoutePane(response.getRouteIdList());
    }

    public void refreshStation(StationController controller) {
        try {
            var iterator = blockingStub.refreshStation(Empty.getDefaultInstance());
            log.debug("set refresh task");
            initStation(controller);
            while (iterator.hasNext()) {
                var response = iterator.next();
                var type = response.getValueCase();
                log.info(type);
                switch (type) {
                    case SIGNAL -> {
                        var signal = response.getSignal();
                        controller.updateSignal(signal);
                        log.debug("get signal: " + signal.getId() + ":" + signal.getState());
                    }
                    case TURNOUT -> {
                        var turnout = response.getTurnout();
                        controller.updateTurnout(turnout);
                        log.debug("get turnout: " + turnout.getId() + ":" + turnout.getState());
                    }
                    case SECTION -> {
                        var section = response.getSection();
                        controller.updateSection(section);
                        log.debug("get section: " + section.getId() + ":" + section.getState());
                    }
                }
            }
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                log.error("連不上了 重試中");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                refreshStation(controller);
            }
        }
    }

    public String createRoute(List<String> buttons) {
        var list = ButtonList.newBuilder().addAllButtonId(buttons).build();
        var request = CreateRouteRequest.newBuilder().setButtons(list).build();
        var response = blockingStub.createRoute(request);
        return response.getRouteId();
    }

    public void cancelRoute(String routeId) {
        var request = CancelRouteRequest.newBuilder().setRouteId(routeId).build();
        var response = blockingStub.cancelRoute(request);
    }
}
