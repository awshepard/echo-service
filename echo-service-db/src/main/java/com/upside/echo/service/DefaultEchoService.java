package com.upside.echo.service;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * <p>Default implementation of our EchoService</p>
 */
public class DefaultEchoService implements EchoService {

    /**
     * @return The environment variables of the running service, with anything related to the ECHO_DB filtered out
     */
    @Override
    public Map<String, String> getEnv() {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        System.getenv().forEach((k, v) -> {
            if (!k.startsWith("ECHO_DB")) {
                builder.put(k, v);
            }
        });
        return builder.build();
    }

}
