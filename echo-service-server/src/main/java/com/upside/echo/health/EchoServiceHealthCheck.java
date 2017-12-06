package com.upside.echo.health;

import com.codahale.metrics.health.HealthCheck;
import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

/**
 * <p>Health check for the echo service.</p>
 */
public class EchoServiceHealthCheck extends NamedHealthCheck {

    @Override
    protected HealthCheck.Result check() throws Exception {
        // FIXME replace with something more meaningful as a test of our health (e.g. db connectivity?)
        return HealthCheck.Result.healthy();
    }

    @Override
    public String getName() {
        return "EchoServiceHealthCheck";
    }
}
