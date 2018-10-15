package com.upside.echo.model;

import com.upside.test.model.AbstractModelTest;
import java.util.UUID;

/**
 * <p>Tests our greeting POJO</p>
 */
public class TestGreeting extends AbstractModelTest<Greeting> {

    @Override
    protected String getFixtureFile() {
        return "greeting.json";
    }

    @Override
    protected Greeting getExpectedEntity() {
        return Greeting.builder()
            .uuid(UUID.fromString("4de5f984-0be3-4db2-97a0-644048cd84f8"))
            .greeting("Hello world")
            .build();
    }
}
