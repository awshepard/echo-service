package com.upside.echo.service;

import com.google.common.collect.ImmutableSet;
import com.upside.echo.model.Greeting;
import com.upside.echo.test.util.AbstractServiceIntTest;
import static com.upside.echo.test.util.Fixtures.GREETING_1;
import static com.upside.echo.test.util.Fixtures.GREETING_2;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>IntTest for our DefaultGreetingService</p>
 */
public class IntTestDefaultGreetingService extends AbstractServiceIntTest {

    private DefaultGreetingService service;

    @Before
    public void setup() {
        this.service = super.injector.getInstance(DefaultGreetingService.class);
    }

    @Test
    public void testUpsertAndGetAll() {
        assertEquals(GREETING_1, this.service.upsert(GREETING_1));
        assertEquals(GREETING_2, this.service.upsert(GREETING_2));

        assertEquals(ImmutableSet.of(GREETING_1, GREETING_2),
                     ImmutableSet.copyOf(this.service.getAllGreetings()));
    }

    @Test
    public void testInsertAndGetByUuid() {
        Greeting newGreeting = this.service.insert("Some new greeting");
        assertEquals("Some new greeting", newGreeting.getGreeting());

        assertEquals(newGreeting, this.service.getGreetingByUuid(newGreeting.getUuid()).get());
    }


}
