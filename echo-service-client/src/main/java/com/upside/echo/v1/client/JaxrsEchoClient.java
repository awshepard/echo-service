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
    private final WebTarget targetEnv;
    private final WebTarget targetHello;
    private final WebTarget targetFail50;

    public JaxrsEchoClient(ExecutorService executorService, String baseUrl, String username, String password) {
        Client client = new JerseyClientBuilder(new MetricRegistry())
                .using(new ObjectMapper()
                    .findAndRegisterModules()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))
//                .using(new DefaultHttpRequestRetryHandler(6, true))
                .using(new UpsideRequestRetryHandler(5, "echo-client"))
                .using(executorService)
                .build("echo-client");
        client.property(ClientProperties.CONNECT_TIMEOUT, CLIENT_TIMEOUT_MS);
        client.property(ClientProperties.READ_TIMEOUT, CLIENT_TIMEOUT_MS);
        client.register(new BasicAuthClientRequestFilter(username, password));

        targetEnv = client.target(String.format("%s/echo/env", baseUrl));
        targetHello = client.target(String.format("%s/echo/hello", baseUrl));
        targetFail50 = client.target(String.format("%s/echo/fail50", baseUrl));
    }

    @Override
    public Map<String, String> getEnv() {
        LOGGER.debug("Getting service from endpoint '{}'", targetEnv.getUri());

        Response response = targetEnv.request(MediaType.APPLICATION_JSON).get();

        LOGGER.debug("Got response '{}'", response.getStatus());
        checkState(response.getStatus() == Response.Status.OK.getStatusCode(),
                "Got status %s instead of 200", response.getStatus());

        return response.readEntity(new GenericType<Map<String, String>>() {});
    }

    @Override
    public String getHello() {
        LOGGER.debug("Getting service from endpoint '{}'", targetHello.getUri());

        Response response = targetHello.request(MediaType.TEXT_PLAIN).get();

        LOGGER.debug("Got response '{}'", response.getStatus());
        checkState(response.getStatus() == Response.Status.OK.getStatusCode(),
                "Got status %s instead of 200", response.getStatus());

        return response.readEntity(String.class);
    }

    @Override
    public String fail50() {
        Response response = null;
        try {
            LOGGER.debug("Getting service from endpoint '{}'", targetFail50.getUri());

            response = targetFail50.request(MediaType.TEXT_PLAIN).get();

            LOGGER.debug("Got response '{}'", response.getStatus());
            checkState(response.getStatus() == Response.Status.OK.getStatusCode(),
                    "Got status %s instead of 200", response.getStatus());

            return response.readEntity(String.class);
        }
        finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
