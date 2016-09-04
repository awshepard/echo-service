package com.upside.echo;

import com.upside.echo.resources.EchoServerResource;
import io.dropwizard.Application;
import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

/**
 * Created by bsiemon on 8/31/16.
 */
public class EchoApplication extends Application<EchoConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            args = new String[] {"server"};
        }
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
        bootstrap.addCommand(new Command("ami-server", "Starts server on configured AMI.") {
            @Override
            public void configure(Subparser subparser) {

            }

            @Override
            public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {

            }
        });
    }

    @Override
    public void run(EchoConfiguration configuration, Environment environment) {
        environment.jersey().register(new EchoServerResource());
    }
}
