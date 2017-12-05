package com.upside.echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.upside.echo.dao.GreetingDAO;
import com.upside.echo.service.DefaultGreetingService;
import com.upside.echo.service.GreetingService;
import com.upside.model.jdbi.argument.UUIDArgumentFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import java.util.UUID;
import java.util.function.Supplier;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Guice Module for wiring together our EchoService</p>
 */
class EchoServiceModule implements Module  {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServiceModule.class);
    private final Environment environment;
    private final EchoConfiguration configuration;

    EchoServiceModule(Environment environment, EchoConfiguration configuration) {
        this.environment = checkNotNull(environment);
        this.configuration = checkNotNull(configuration);
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(GreetingService.class).to(DefaultGreetingService.class).asEagerSingleton();
    }

    @Singleton
    @Provides
    public DBI prepareJdbi() throws ClassNotFoundException {
        LOGGER.debug("Guice providing new DBI");
        final DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(this.environment, this.configuration.getDatabase(), "mysql");
        jdbi.registerArgumentFactory(new UUIDArgumentFactory());
        return jdbi;
    }

    @Provides
    public GreetingDAO provideGreetingDAO(DBI dbi) {
        LOGGER.debug("Guice providing new GreetingDAO");
        return dbi.onDemand(GreetingDAO.class);
    }

    @Singleton
    @Provides
    public ObjectMapper provideObjectMapper() {
        LOGGER.debug("Guice providing new ObjectMapper");
        return this.environment.getObjectMapper();
    }

    @Singleton
    @Provides
    public Supplier<UUID> provideUuidSupplier() {
        LOGGER.debug("Guice providing new UUIDSupplier");
        return () -> UUID.randomUUID();
    }
}
