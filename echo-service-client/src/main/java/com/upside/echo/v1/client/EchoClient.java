package com.upside.echo.v1.client;

import java.util.Map;


/**
 * <p>Interface for interacting with the attribute service</p>
 */
public interface EchoClient {

    Map<String, String> getEnv();

    String getHello();

    /**
     * @return says "hello" 50% of the time, throws a 500 InternalServerException the other 50% of the time
     */
    String fail50();

}
