package com.upside.echo.v1.client;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.upside.lib.rest.auth.BasicAuthClientRequestFilter;
import com.upside.lib.rest.client.AbstractJaxrsClient;
import io.dropwizard.client.JerseyClientBuilder;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * <p>JAXRS implemention of our EchoClient contract</p>
 */
public class JaxrsEchoClient extends AbstractJaxrsClient implements EchoClient {

    // This is intentionally lower (5s) than the sleep in the timeout50 endpoint so that the call will timeout and we can
    // test retries
    private static final int CLIENT_TIMEOUT_MS = 5000;
    private final Client client;
    private final String baseUrl;
    private final WebTarget targetEnv;
    private final WebTarget targetHello;
    private final WebTarget targetTimeout50;
    private final WebTarget targetServiceUnavailable50;

    public JaxrsEchoClient(ExecutorService executorService, String baseUrl, String username, String password) {
        super(5);
        this.client = new JerseyClientBuilder(new MetricRegistry())
                .using(new ObjectMapper()
                    .findAndRegisterModules()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))
                .using(executorService)
                .build("echo-client");
        this.client.property(ClientProperties.CONNECT_TIMEOUT, CLIENT_TIMEOUT_MS);
        this.client.property(ClientProperties.READ_TIMEOUT, CLIENT_TIMEOUT_MS);
        this.client.register(new BasicAuthClientRequestFilter(username, password));
        this.baseUrl = baseUrl;

        this.targetEnv = client.target(String.format("%s/echo/env", this.baseUrl));
        this.targetHello = client.target(String.format("%s/echo/hello", this.baseUrl));
        this.targetTimeout50 = client.target(String.format("%s/echo/timeout50", this.baseUrl));
        this.targetServiceUnavailable50 = client.target(String.format("%s/echo/serviceUnavailable50", this.baseUrl));
    }

    @Override
    public Map<String, String> getEnv() {
        LOGGER.debug("Getting service from endpoint '{}'", this.targetEnv.getUri());
        return retry(() -> this.targetEnv.request(MediaType.APPLICATION_JSON).get(), new GenericType<Map<String, String>>() {});
    }

    @Override
    public String getHello() {
        LOGGER.debug("Getting service from endpoint '{}'", this.targetHello.getUri());
        return retry(() -> this.targetHello.request(MediaType.TEXT_PLAIN).get(), String.class);
    }

    @Override
    public String timeout50() {
        LOGGER.debug("Getting service from endpoint '{}'", this.targetTimeout50.getUri());
        return retry(() -> this.targetTimeout50.request(MediaType.TEXT_PLAIN).get(), String.class);
    }

    @Override
    public String serviceUnavailable50() {
        LOGGER.debug("Getting service from endpoint '{}'", this.targetServiceUnavailable50.getUri());
        return retry(() -> this.targetServiceUnavailable50.request(MediaType.TEXT_PLAIN).get(), String.class);
    }

    @Override
    public String get(String endpoint) {
        WebTarget target = this.client.target(String.format("%s/%s", this.baseUrl, endpoint));
        LOGGER.debug("Getting service from endpoint '{}'", target.getUri());
        return retry(() -> target.request(MediaType.TEXT_PLAIN).get(), String.class);
    }

}
