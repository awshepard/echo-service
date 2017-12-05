package com.upside.echo.dao;

import com.upside.echo.model.Greeting;
import com.upside.echo.test.util.AbstractServiceIntTest;
import static com.upside.echo.test.util.Fixtures.GREETING_1;
import static com.upside.echo.test.util.Fixtures.GREETING_2;
import java.util.Collection;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Tests our GreetingDAO</p>
 */
public class IntTestGreetingDAO extends AbstractServiceIntTest {

    private GreetingDAO dao;

    @Before
    public void setup() {
        this.dao = super.injector.getInstance(GreetingDAO.class);
    }

    @Test
    public void testUpsertAndFindAll() {
        this.dao.upsert(GREETING_1);
        this.dao.upsert(GREETING_2);
        Collection<Greeting> allGreetings = this.dao.findGreetings();

        assertTrue("Expected 2 or more greetings, but got " + allGreetings.size(), allGreetings.size() >= 2);
        assertTrue(allGreetings.contains(GREETING_1));
        assertTrue(allGreetings.contains(GREETING_2));
    }

    @Test
    public void testUpsertAndFindByUuid() {
        this.dao.upsert(GREETING_1);

        assertEquals(GREETING_1, this.dao.findByUuid(GREETING_1.getUuid()));
    }

    @Test
    public void testGreetingNotFound() {
        assertNull(this.dao.findByUuid(UUID.randomUUID()));
    }
}
