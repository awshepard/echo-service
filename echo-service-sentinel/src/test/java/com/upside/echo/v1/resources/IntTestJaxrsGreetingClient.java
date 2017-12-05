package com.upside.echo.v1.resources;

import com.upside.echo.AbstractResourceIntegrationTest;
import static com.upside.echo.AbstractResourceIntegrationTest.RULE;
import com.upside.echo.model.Greeting;
import com.upside.echo.v1.client.greeting.JaxrsGreetingClient;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Tests the JaxrsGreetingClient</p>
 */
public class IntTestJaxrsGreetingClient extends AbstractResourceIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    final JaxrsGreetingClient client = new JaxrsGreetingClient(new ForkJoinPool(),
        "http://localhost:" + RULE.getLocalPort(), "testuser", "secret");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * The production patch V1.0.0.0 in the -db artifact inserts 2 sample greetings, so there should be at least 2 when
     * we ask for "all"
     */
    @Test
    public void testGetAll() {
        Collection<Greeting> greetings = this.client.getGreetings();
        assertTrue(greetings.size() >= 2);
    }

    @Test
    public void testGetByUuid() {
        Greeting greeting = this.client.getGreetingByUuid(UUID.fromString("b871bee0-0eca-447a-ae3c-741d475d1a12"));
        assertEquals("It is a pleasure to meet you.", greeting.getGreeting());
    }
}
