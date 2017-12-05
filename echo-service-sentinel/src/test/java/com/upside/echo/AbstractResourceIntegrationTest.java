package com.upside.echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upside.test.mysql.MySQLRule;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.io.IOException;
import java.time.Instant;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.rules.RuleChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Common integration test setup stuff</p>
 */
public class AbstractResourceIntegrationTest {

    static final Logger LOGGER = LoggerFactory.getLogger(AbstractResourceIntegrationTest.class);
    public static final Instant NOW = Instant.now();
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    static final MySQLRule MYSQL_RULE = MySQLRule.defaultRule();

    public static final DropwizardAppRule<EchoConfiguration> RULE =
        new DropwizardAppRule<>(EchoApplication.class, ResourceHelpers.resourceFilePath("test_config.yml"),
        ConfigOverride.config("server.applicationConnectors[0].port", "0"),
        ConfigOverride.config("server.adminConnectors[0].port", "0"),
        ConfigOverride.config("database.url", MYSQL_RULE.getDbUrl()),
        ConfigOverride.config("database.user", MYSQL_RULE.getDbUser()),
        ConfigOverride.config("database.password", MYSQL_RULE.getDbPassword())
    );

    @ClassRule
    public static final RuleChain ORDERED_RULES = RuleChain.outerRule(MYSQL_RULE).around(RULE);

    public static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public static final int CLIENT_TIMEOUT_MS = 45000;

    @Before
    public void before() throws IOException {
    }

    @BeforeClass
    public static void beforeClass() throws IOException {
    }
}
