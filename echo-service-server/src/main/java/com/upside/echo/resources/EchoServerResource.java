package com.upside.echo.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ServiceUnavailableException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Random;

/**
 * Echo server fun times.
 */
public class EchoServerResource implements EchoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Random random = new Random();

    @Override
    public Map<String, String> getEnv() {
        return System.getenv();
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
