package com.upside.echo.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.inject.Inject;
import com.upside.echo.dao.GreetingDAO;
import com.upside.echo.model.Greeting;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Default implementation for our GreetingService</p>
 */
public class DefaultGreetingService implements GreetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Supplier<UUID> uuidSupplier;
    private final GreetingDAO dao;

    @Inject
    public DefaultGreetingService(Supplier<UUID> uuidSupplier,
                                  GreetingDAO dao) {
        LOGGER.debug("Constructing new DefaultGreetingService");
        this.uuidSupplier = checkNotNull(uuidSupplier);
        this.dao = checkNotNull(dao);
    }

    @Override
    public Collection<Greeting> getAllGreetings() {
        return this.dao.findGreetings();
    }

    @Override
    public Optional<Greeting> getGreetingByUuid(UUID uuid) {
        checkNotNull(uuid, "Must not attempt to getGreetingByUuid with null UUID");
        return Optional.ofNullable(this.dao.findByUuid(uuid));
    }

    @Override
    public Greeting insert(String greeting) {
        UUID newGreetingUuid = this.uuidSupplier.get();
        return this.upsert(Greeting.create(newGreetingUuid, greeting));
    }

    @Override
    public Greeting upsert(Greeting greeting) {
        this.dao.upsert(greeting);
        return this.getGreetingByUuid(greeting.getUuid())
            .orElseThrow(() -> new IllegalStateException("Could not find Greeting I just upserted!"));
    }

}
