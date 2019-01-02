package com.upside.echo.health;

import com.codahale.metrics.health.HealthCheck;
import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

/**
 * <p>Health check for the echo service.</p>
 */
public class EchoServiceHealthCheck extends NamedHealthCheck {

    @Override
    protected HealthCheck.Result check() throws Exception {
        return HealthCheck.Result.unhealthy("this is a test of an unhealthy echo-service");
    }

    @Override
    public String getName() {
        return "EchoServiceHealthCheck";
    }
}
