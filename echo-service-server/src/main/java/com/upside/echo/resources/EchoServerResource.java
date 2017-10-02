package com.upside.echo.resources;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public String fail50() throws IOException {
        if (this.random.nextFloat() < 0.5f) {
            LOGGER.warn("Server throwing random error");
            throw new IOException("This is a randomly occuring error");
        }
        else {
            LOGGER.info("Server returning 'hello'");
            return "hello";
        }
    }
}
