package io.hreem.studies;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.logging.Log;

@Path("/hello")
@ApplicationScoped
public class GreetingResource {

    @GET
    @Path("/404")
    @Produces(MediaType.TEXT_PLAIN)
    public Response helloFailing() {
        Log.infof("Called hello with 404");
        return Response.status(404).build();
    }

    @GET
    @Path("/500")
    @Produces(MediaType.TEXT_PLAIN)
    public Response helloFailingWithInternalServerError() {
        Log.infof("Called hello with 500");
        return Response.status(500).build();
    }
}