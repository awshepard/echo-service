package com.upside.echo.v1.client.greeting;

import com.google.common.base.Preconditions;
import com.upside.lib.rest.client.AbstractJaxrsMockSupportingClientFactory;
import java.util.concurrent.ForkJoinPool;


/**
 /**
 * <p>Dropwizard-style factory to produce a GreetingClient.</p>
 * <p>Example configuration in a Dropwizard YAML file:</p>
 * <pre>
  greetingClient:
    baseUrl: ${ECHO_SERVICE_URL}
    username: ${ECHO_SERVICE_USER}
    password: ${ECHO_SERVICE_PASS}
 * </pre>
 */
public class GreetingClientFactory extends AbstractJaxrsMockSupportingClientFactory<GreetingClient> {

    /**
     * @return If "type" is set to "MOCK" and a "mockData" property is provided to load fake Attributes into the client, then
     * a new MockAttributeClient will be built.  Otherwise a real JaxrsAttributeClient will be returned.
     */
    @Override
    public GreetingClient build() {
        if (TYPE_MOCK.equalsIgnoreCase(getType())) {
            throw new UnsupportedOperationException();
        }
        else {
            Preconditions.checkNotNull(getBaseUrl());
            Preconditions.checkNotNull(getUsername());
            Preconditions.checkNotNull(getPassword());
            return new JaxrsGreetingClient(new ForkJoinPool(), getBaseUrl(), getUsername(), getPassword());
        }
    }
}
