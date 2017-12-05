package com.upside.echo.v1.resources;

import com.upside.echo.AbstractResourceIntegrationTest;
import com.upside.echo.v1.client.echo.JaxrsEchoClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * <p>Tests our JaxrsAttributeClient implementation</p>
 * <p>Any "magic strings" you see in this IntTest that you wonder where they come from are probably coming from a test
 * fixture "patch" that gets applied to the server's DB.  This is contained in this sentinel's src/test/resources/db/migration
 * directory</p>
 */
public class IntTestJaxrsEchoClient extends AbstractResourceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    final JaxrsEchoClient client = new JaxrsEchoClient(new ForkJoinPool(),
        "http://localhost:" + RULE.getLocalPort(), "testuser", "secret");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetEnv() {
        Map<String, String> env = this.client.getEnv();
        assertFalse("Not expecting an empty env Map", env.isEmpty());
    }

    @Test
    public void testHello() {
        assertEquals("hello", this.client.getHello());
    }

    /**
     * This test verifies that the client is properly configured to retry requests that fail with an exception.
     */
    @Test
    public void testTimeout50() {
        for (int i=0; i<10; i++) {
            LOGGER.debug("Trial {} of testFail50", i);
            assertEquals("hello", this.client.timeout50());
        }
    }

    /**
     * This test verifies that the client is properly configured to retry requests that return a 502, 503, or 504 response.
     */
    @Test
    public void testServiceUnavailable50() {
        for (int i=0; i<10; i++) {
            LOGGER.debug("Trial {} of testFail50", i);
            assertEquals("hello", this.client.serviceUnavailable50());
        }
    }

    @Test
    public void testNonExistentEndpoint() {
        this.exception.expect(NotFoundException.class);
        this.client.get("fail");
    }

}

