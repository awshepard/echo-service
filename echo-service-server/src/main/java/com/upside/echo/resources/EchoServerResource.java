package com.upside.echo.resources;

import java.util.Map;
import java.util.Random;

/**
 * Echo server fun times.
 */
public class EchoServerResource implements EchoResource {

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
    public String fail50() {
        if (this.random.nextFloat() < 0.5f) {
            throw new IllegalStateException("This is a randomly occuring error");
        }
        else {
            return "hello";
        }
    }
}
