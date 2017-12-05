package com.upside.echo.resources;

import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Echo resource.
 */
@Path("/echo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EchoResource {

    @Path("/env")
    @GET
    Map<String, String> getEnv();

    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    @GET
    String getHello();

    /**
     * @return says "hello" immediately 50% of the time, 50% of the time sleeps for 10 seconds before returning "hello"
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/timeout50")
    @GET
    String timeout50() throws InterruptedException;

    /**
     * @return says "hello" 50% of the time, returns a 503 Service Unavailable 50% of the time
     */
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/serviceUnavailable50")
    @GET
    String serviceUnavailable50();

}
