package com.upside.echo.v1.client.greeting;

import com.upside.echo.model.Greeting;
import java.util.Collection;
import java.util.UUID;


/**
 * <p>Interface for any implementing GreetingClient</p>
 */
public interface GreetingClient {

    Collection<Greeting> getGreetings();

    Greeting getGreetingByUuid(UUID greeting);
}
