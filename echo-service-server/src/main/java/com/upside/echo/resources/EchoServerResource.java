package com.upside.echo.resources;

import com.upside.echo.service.EchoService;
import com.washingtonpost.dw.auth.model.Peer;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;
import javax.ws.rs.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Echo server fun times.
 */
public class EchoServerResource implements EchoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Random random = new Random();
    private final EchoService service;

    @Inject
    public EchoServerResource(EchoService echoService) {
        this.service = echoService;
    }

    @Override
    public Map<String, String> getEnv(Peer peer) {
        return this.service.getEnv();
    }

    @Override
    public String getHello() {
        return "hello";
    }

    @Override
    public String timeout50() throws InterruptedException {
        if (this.random.nextFloat() < 0.5f) {
            LOGGER.warn("Server sleeping for 10s");
            Thread.sleep(10000);
        }
        LOGGER.info("Server returning 'hello'");
        return "hello";
    }

    @Override
    public String serviceUnavailable50() {
        if (this.random.nextFloat() < 0.5f) {
            LOGGER.warn("Server throwing random error");
            throw new ServiceUnavailableException("This is a randomly occuring error");
        }
        LOGGER.info("Server returning 'hello'");
        return "hello";
    }

}
