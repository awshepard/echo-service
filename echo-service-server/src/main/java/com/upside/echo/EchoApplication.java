package com.upside.echo;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.lang.invoke.MethodHandles;
import org.flywaydb.core.Flyway;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

/**
 * Created by bsiemon on 8/31/16.
 */
public class EchoApplication extends Application<EchoConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private GuiceBundle guiceBundle;


    public static void main(String[] args) throws Exception {
        new EchoApplication().run(args);
    }

    public EchoApplication() {
    }

    @Override
    public String getName() {
        return "echo-service";
    }

    @Override
    public void initialize(Bootstrap<EchoConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                                               new EnvironmentVariableSubstitutor(false)
                )
        );

        guiceBundle = GuiceBundle.builder()
            .enableAutoConfig("com.upside",         // 1) wide net to allow for libraries
                              "com.washingtonpost") // 2) for our JSON Exception handlers
            .modules(new DropwizardAwareModule<EchoConfiguration>() {
                @Override
                protected void configure() {
                    install(new EchoServiceModule(environment(), configuration()));
                }
            })
            //.printDiagnosticInfo()   // comment in for verbose details about Guice startup
            .build();

        bootstrap.addBundle(this.guiceBundle);
    }

    @Override
    public void run(EchoConfiguration configuration, Environment environment) {
        migrateDatabase(configuration.getDatabase());

        configuration.getAllowedPeers().registerAuthenticator(environment);

        // Force the DBI to be instantiated now in order to allow the ManagedDataSource to be attached to the lifecycle
        // through {@code io.dropwizard.lifecycle.setup.LifecycleEnvironment#attach()}, which is only called at
        // startup. If we don't do this here, the DBI won't be instantiated until an endpoint is called, and
        // the data source will not be properly started and shutdown by Dropwizard. This means we wouldn't get
        // ManagedPooledDataSource metrics and connections would not be closed on shutdown.
        this.guiceBundle.getInjector().getProvider(DBI.class).get();
    }

    private void migrateDatabase(DataSourceFactory dataSourceFactory) {
        LOGGER.info("Starting database migration");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
        flyway.migrate();
        LOGGER.info("Database migration complete");
    }
}
