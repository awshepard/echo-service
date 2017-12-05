package com.upside.echo.v1.client.greeting;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.upside.echo.model.Greeting;
import com.upside.lib.rest.auth.BasicAuthClientRequestFilter;
import com.upside.lib.rest.client.AbstractJaxrsClient;
import io.dropwizard.client.JerseyClientBuilder;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.glassfish.jersey.client.ClientProperties;

/**
 * <p>JAXRS implementation of our GreetingClient</p>
 */
public class JaxrsGreetingClient extends AbstractJaxrsClient implements GreetingClient {

    // This is intentionally lower (5s) than the sleep in the timeout50 endpoint so that the call will timeout and we can
    // test retries
    private static final int CLIENT_TIMEOUT_MS = 5000;
    private final Client client;
    private final String baseUrl;
    private final WebTarget targetGreeting;

    public JaxrsGreetingClient(ExecutorService executorService, String baseUrl, String username, String password) {
        super(5);
        this.client = new JerseyClientBuilder(new MetricRegistry())
                .using(new ObjectMapper()
                    .findAndRegisterModules()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))
                .using(executorService)
                .build("greeting-client");
        this.client.property(ClientProperties.CONNECT_TIMEOUT, CLIENT_TIMEOUT_MS);
        this.client.property(ClientProperties.READ_TIMEOUT, CLIENT_TIMEOUT_MS);
        this.client.register(new BasicAuthClientRequestFilter(username, password));
        this.baseUrl = baseUrl;

        this.targetGreeting = client.target(String.format("%s/api/v1/greeting", this.baseUrl));
    }

    @Override
    public Collection<Greeting> getGreetings() {
        LOGGER.debug("Getting service from endpoint '{}'", this.targetGreeting.getUri());
        return retry(() -> this.targetGreeting.request(APPLICATION_JSON).get(), new GenericType<Collection<Greeting>>() {});
    }

    @Override
    public Greeting getGreetingByUuid(UUID greeting) {

        final WebTarget target = targetGreeting.path(greeting.toString());

        return retry(() -> target.request(APPLICATION_JSON).get(), Greeting.class);
    }

}
