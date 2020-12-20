package dev.glycine.pracstation.service;

import com.google.protobuf.Empty;
import dev.glycine.pracstation.controllers.StationController;
import dev.glycine.pracstation.pb.StationServiceGrpc;
import dev.glycine.pracstation.pb.StationServiceGrpc.StationServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
public class StationClient {
    private final ManagedChannel channel;
    private final StationServiceBlockingStub blockingStub;

    public StationClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = StationServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void refreshStation(StationController controller) {
        log.info("refresh started");
        new Thread(() -> {
            try {
                var iterator = blockingStub
                        .refreshStation(Empty.getDefaultInstance());
                while (true) {
                    if (!iterator.hasNext()) continue;
                    var response = iterator.next();
                    var type = response.getValueCase();
                    log.info(type);
                    switch (type) {
                        case SIGNAL -> {
                            var signal = response.getSignal();
                            controller.updateSignal(signal);
                            log.info("get signal: " + signal.getId() + ":" + signal.getState());
                        }
                        case TURNOUT -> {
                            var turnout = response.getTurnout();
                            controller.updateTurnout(turnout);
                            log.info("get turnout: " + turnout.getId() + ":" + turnout.getState());
                        }
                        case SECTION -> {
                            var section = response.getSection();
                            controller.updateSection(section);
                            log.info("get section: " + section.getId() + ":" + section.getState());
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("request failed: " + e.getMessage());
            }
        }).start();
        log.info("steam complete");
    }
}
