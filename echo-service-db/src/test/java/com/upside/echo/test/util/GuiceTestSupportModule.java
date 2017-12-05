package com.upside.echo.test.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.upside.echo.dao.GreetingDAO;
import com.upside.echo.service.DefaultGreetingService;
import com.upside.echo.service.GreetingService;
import java.util.UUID;
import java.util.function.Supplier;
import org.skife.jdbi.v2.DBI;


/**
 * <p>Test support guice wiring for our Integration Tests</p>
 */
public class GuiceTestSupportModule extends AbstractModule {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private DBI dbi;
    private Supplier<UUID> uuidSupplier;

    public static Builder builder() {
        return new Builder();
    }

    /**
     * This builder currently only contains setters for dependencies we override in existing IntTests; feel free to add
     * more setters as necessary...
     */
    public static class Builder {
        private DBI dbi;
        private Supplier<UUID> uuidSupplier = () -> UUID.fromString("00000000-0000-0000-0000-000000000000");

        public Builder dbi(DBI dbi) {
            this.dbi = dbi;
            return this;
        }

        public Builder uuidSupplier(Supplier<UUID> uuidSupplier) {
            this.uuidSupplier = uuidSupplier;
            return this;
        }

        public GuiceTestSupportModule build() {
            Preconditions.checkNotNull(this.dbi, "Must provide a DBI");

            GuiceTestSupportModule module = new GuiceTestSupportModule();
            module.dbi = this.dbi;
            module.uuidSupplier = this.uuidSupplier;
            return module;
        }
    }

    private GuiceTestSupportModule() {
    }

    @Override
    protected void configure() {
        bind(GreetingService.class).to(DefaultGreetingService.class);
    }

    @Provides
    public DBI providesDbi() {
        return this.dbi;
    }

    @Provides
    public Supplier<UUID> provideNowSupplier() {
        if (this.uuidSupplier != null) {
            return this.uuidSupplier;
        }
        return () -> UUID.randomUUID();
    }

    @Provides
    public GreetingDAO provideGreetingDAO(DBI dbi) {
        return dbi.onDemand(GreetingDAO.class);
    }
}
