package com.upside.echo.v1.client.echo;

import java.util.Map;


/**
 * <p>Interface for interacting with the attribute service</p>
 */
public interface EchoClient {

    Map<String, String> getEnv();

    String getHello();

    /**
     * @return says "hello" immediately 50% of the time, sleeps for 10 seconds before saying "hello" the other 50% of the time
     */
    String timeout50();

    /**
     * @return says "hello" 50% of the time, throws a 503 Service Unavailable the other 50% of the time
     */
    String serviceUnavailable50();

    /**
     * @param endpoint the endpoint to request service from
     * @return the String response from the given endpoint
     */
    String get(String endpoint);

}
