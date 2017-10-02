package com.upside.echo.resources;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

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
     * @return says "hello" 50% of the time, throws an IOException
     * @throws java.io.IOException thrown 50% of the time
     */
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/fail50")
    @GET
    String fail50() throws IOException ;

}
