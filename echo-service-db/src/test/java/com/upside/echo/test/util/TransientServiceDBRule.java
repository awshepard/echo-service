package com.upside.echo.test.util;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.upside.model.jdbi.argument.UUIDArgumentFactory;
import com.upside.test.mysql.MySQLRule;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 * Test rule to setup and teardown a database for testing.
 */
public class TransientServiceDBRule extends ExternalResource {

    private final List<String> tables;
    private final MySQLRule mySQLRule;
    private ManagedDataSource dataSource;
    private DBI dbi;

    public TransientServiceDBRule(MySQLRule rule, String ... tables) {
        this.mySQLRule = rule;
        this.tables = ImmutableList.copyOf(tables);
    }

    public MySQLRule getMySQLRule() {
        return this.mySQLRule;
    }

    public DBI getDbi() {
        return this.dbi;
    }

    @Override
    protected void before() throws Throwable {
        Environment environment = new Environment("test-env",
                                                  new ObjectMapper().findAndRegisterModules(),
                                                  null,
                                                  new MetricRegistry(),
                                                  null);

        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setUrl(this.mySQLRule.getDbUrl());
        dataSourceFactory.setUser(this.mySQLRule.getDbUser());
        dataSourceFactory.setPassword(this.mySQLRule.getDbPassword());
        dataSourceFactory.setDriverClass(MySQLRule.DRIVER_CLASSNAME);
        this.dataSource = dataSourceFactory.build(new MetricRegistry(), "test");
        this.dbi = new DBIFactory().build(environment, dataSourceFactory, this.dataSource, "test");
        this.dbi.registerArgumentFactory(new UUIDArgumentFactory());

        Flyway flyway = new Flyway();
        flyway.setDataSource(this.mySQLRule.getDbUrl(),
                             this.mySQLRule.getDbUser(),
                             this.mySQLRule.getDbPassword());
        flyway.migrate();

        cleanDb();
   }

    @Override
    public void after() {
        cleanDb();
        try {
            this.dataSource.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanDb() {
        this.dbi.withHandle((Handle handle) -> {
            TransientServiceDBRule.this.tables.forEach((table) -> {
                handle.execute(String.format("delete from %s;", table));
            });
            return null;
        });
    }
}
