package com.upside.echo.resources;

import static com.google.common.base.Preconditions.checkNotNull;
import com.upside.echo.model.Greeting;
import com.upside.echo.service.GreetingService;
import com.washingtonpost.dw.auth.model.Peer;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Default JAXRS implementation for the GreetingResource</p>
 */
public class GreetingServerResource implements GreetingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final GreetingService service;

    @Inject
    public GreetingServerResource(GreetingService service) {
        LOGGER.debug("Constructing a new GreetingServerResource");
        this.service = checkNotNull(service);
    }

    @Override
    public Collection<Greeting> getGreetings(Peer peer) {
        return this.service.getAllGreetings();
    }

    @Override
    public Greeting getGreeting(Peer peer, UUID uuid) {
        return this.service.getGreetingByUuid(uuid)
            .orElseThrow(() -> new NotFoundException("No such Greeting with UUID " + uuid + " found!"));

    }
}
