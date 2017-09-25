package com.upside.echo.v1.client;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import static com.google.common.base.Preconditions.checkState;
import com.upside.lib.rest.auth.BasicAuthClientRequestFilter;
import io.dropwizard.client.JerseyClientBuilder;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>JAXRS implemention of our EchoClient contract</p>
 */
public class JaxrsEchoClient implements EchoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int CLIENT_TIMEOUT_MS = 45000;
    private final String baseUrl;
    private final Client client;

    public JaxrsEchoClient(ExecutorService executorService, String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.client = new JerseyClientBuilder(new MetricRegistry())
                .using(new ObjectMapper()
                    .findAndRegisterModules()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))
                .using(executorService)
                .build("attribute-client");
        this.client.property(ClientProperties.CONNECT_TIMEOUT, CLIENT_TIMEOUT_MS);
        this.client.property(ClientProperties.READ_TIMEOUT, CLIENT_TIMEOUT_MS);
        this.client.register(new BasicAuthClientRequestFilter(username, password));
    }

    @Override
    public Map<String, String> getEnv() {
        WebTarget target = this.client.target(String.format("%s/echo/env", this.baseUrl));
        LOGGER.debug("Getting service from endpoint '{}'", target.getUri());

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        LOGGER.debug("Got response '{}'", response.getStatus());
        checkState(response.getStatus() == Response.Status.OK.getStatusCode(),
                "Got status %s instead of 200", response.getStatus());

        return response.readEntity(new GenericType<Map<String, String>>() {});
    }

    @Override
    public String getHello() {
        WebTarget target = this.client.target(String.format("%s/echo/hello", this.baseUrl));
        LOGGER.debug("Getting service from endpoint '{}'", target.getUri());

        Response response = target.request(MediaType.TEXT_PLAIN).get();

        LOGGER.debug("Got response '{}'", response.getStatus());
        checkState(response.getStatus() == Response.Status.OK.getStatusCode(),
                "Got status %s instead of 200", response.getStatus());

        return response.readEntity(String.class);
    }

    @Override
    public String fail50() {
        WebTarget target = this.client.target(String.format("%s/echo/fail50", this.baseUrl));
        LOGGER.debug("Getting service from endpoint '{}'", target.getUri());

        Response response = target.request(MediaType.TEXT_PLAIN).get();

        LOGGER.debug("Got response '{}'", response.getStatus());
        checkState(response.getStatus() == Response.Status.OK.getStatusCode(),
                "Got status %s instead of 200", response.getStatus());

        return response.readEntity(String.class);
    }
}
