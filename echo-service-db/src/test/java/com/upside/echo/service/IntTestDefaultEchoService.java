package com.upside.echo.service;

import com.upside.echo.test.util.AbstractServiceIntTest;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Tests the DefaultEchoService</p>
 */
public class IntTestDefaultEchoService extends AbstractServiceIntTest {

    private DefaultEchoService service;

    @Before
    public void setup() {
        this.service = super.injector.getInstance(DefaultEchoService.class);
    }

    @Test
    public void testGetEnv() {
        Map<String, String> env = this.service.getEnv();
        env.keySet().forEach((key) -> {
            assertFalse(key.startsWith("ECHO_DB"));
        });
    }

}
