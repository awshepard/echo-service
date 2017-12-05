package com.upside.echo.resources;

import com.upside.echo.model.Greeting;
import java.util.Collection;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>REST endpoint for greetings</p>
 */
@Path("/api/v1/greeting")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GreetingResource {

    /**
     * @return all greetings in our database
     */
    @GET
    Collection<Greeting> getGreetings();

    /**
     *
     * @param uuid the UUID of the greeting
     * @return A specific greeting from our database
     */
    @GET
    @Path("/{uuid}")
    Greeting getGreeting(@PathParam("uuid") UUID uuid);

}
