package com.upside.echo.service;

import com.upside.echo.model.Greeting;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Defines the interface for greeting</p>
 */
public interface GreetingService {

    /**
     * @return All greetings
     */
    Collection<Greeting> getAllGreetings();

    /**
     * @param uuid The UUID of the greeting to fetch
     * @return The Greeting, if found.
     */
    Optional<Greeting> getGreetingByUuid(UUID uuid);

    /**
     * @param greeting A new greeting string
     * @return The greeting
     */
    Greeting insert(String greeting);

    /**
     * @param greeting An existing or new greeting
     * @return The greeting
     */
    Greeting upsert(Greeting greeting);
}
