package com.upside.echo.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.upside.model.jdbi.argument.UUIDArgumentFactory;
import com.upside.test.mysql.MySQLRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.skife.jdbi.v2.DBI;

/**
 * <p>Abstract Guice/MySql int test</p>
 */
public class AbstractServiceIntTest {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    @ClassRule
    public static final MySQLRule MYSQL_RULE = MySQLRule.defaultRule();

    @Rule
    public final TransientServiceDBRule dbRule = AbstractServiceIntTest.buildPromoCodeRule(MYSQL_RULE);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    protected Injector injector;

    @Before
    public void before() {
        injector = Guice.createInjector(getGuiceBuilder().build());
    }

    protected GuiceTestSupportModule.Builder getGuiceBuilder() {
        return GuiceTestSupportModule.builder().dbi(assembleDBI());
    }

    protected DBI assembleDBI() {
        DBI dbi = dbRule.getDbi();
        dbi.registerArgumentFactory(new UUIDArgumentFactory());
        return dbi;
    }

    public static TransientServiceDBRule buildPromoCodeRule(MySQLRule mySQLRule) {
        return new TransientServiceDBRule(mySQLRule,
                                          "greeting");
    }
}
