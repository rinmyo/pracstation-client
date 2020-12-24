package dev.glycine.pracstation.service;

import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
public final class TokenManager {
    @Getter
    private final AuthClient client = new AuthClient("0.0.0.0", 8080);

    @Getter
    private Token token;

    public void setToken(String tokenStr) {
        log.debug("get token: "+tokenStr);
        token = new Token(tokenStr);
    }
}
