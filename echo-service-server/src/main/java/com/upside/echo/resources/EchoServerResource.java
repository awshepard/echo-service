package com.upside.echo.resources;

import java.util.Map;

/**
 * Echo server fun times.
 */
public class EchoServerResource implements EchoResource {
    @Override
    public Map<String, String> getEnv() {
        return System.getenv();
    }

    @Override
    public String getHello() {
        return "good night booty";
    }
}
