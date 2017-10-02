package com.upside.echo.v1.resources;

import com.upside.echo.v1.client.JaxrsEchoClient;
import com.upside.echo.AbstractResourceIntegrationTest;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Test
    public void testFail50() {
        for (int i=0; i<10; i++) {
            LOGGER.debug("Trial {} of testFail50", i);
            assertEquals("hello", this.client.fail50());
        }
    }

}

