package dev.glycine.pracstation.service;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.Executor;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;


@AllArgsConstructor
public class Token extends CallCredentials {
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);

    @Getter
    private String value;

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {

        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(AUTHORIZATION_METADATA_KEY, value);
                applier.apply(headers);
            } catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }
}
