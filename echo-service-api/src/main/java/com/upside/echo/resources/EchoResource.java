package com.upside.echo.resources;

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

    @Path("/hello")
    @GET
    String getHello();

}
