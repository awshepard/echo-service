package com.upside.echo.test.util;

import com.upside.echo.model.Greeting;
import java.util.UUID;

/**
 * <p>Helper test fixtures for various Integration tests</p>
 */
public final class Fixtures {

    public static final Greeting GREETING_1 = Greeting.create(UUID.fromString("4de5f984-0be3-4db2-97a0-644048cd84f8"),
                                                              "Hello!");

    public static final Greeting GREETING_2 = Greeting.create(UUID.fromString("b871bee0-0eca-447a-ae3c-741d475d1a12"),
                                                              "It is a pleasure to meet you.");
}
