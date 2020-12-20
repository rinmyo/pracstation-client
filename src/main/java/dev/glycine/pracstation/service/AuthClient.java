package dev.glycine.pracstation.service;

import dev.glycine.pracstation.pb.AuthServiceGrpc;
import dev.glycine.pracstation.pb.LoginRequest;
import dev.glycine.pracstation.pb.LoginResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
public class AuthClient {
    private final ManagedChannel channel;
    private final AuthServiceGrpc.AuthServiceBlockingStub blockingStub;

    public AuthClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String login(String username, String password) throws StatusRuntimeException {
        LoginRequest request = LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
        LoginResponse response = blockingStub.login(request);
        return response.getAccessToken();
    }
}
