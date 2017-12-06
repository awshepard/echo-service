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
        Map<String, String> env = System.getenv();
        env.keySet().stream().filter((key) -> (key.startsWith("ECHO_DB"))).forEachOrdered((key) -> {
            env.remove(key);
        });
        return ImmutableMap.copyOf(env);
    }

}
