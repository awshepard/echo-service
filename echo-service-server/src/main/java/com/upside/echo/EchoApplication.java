package com.upside.echo;

import com.upside.echo.resources.EchoServerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by bsiemon on 8/31/16.
 */
public class EchoApplication extends Application<EchoConfiguration> {

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
    }

    @Override
    public void run(EchoConfiguration configuration, Environment environment) {
        environment.jersey().register(new EchoServerResource());
    }
}
