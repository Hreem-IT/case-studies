package io.hreem.casestudies.order.gateway;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/barista")
@RegisterRestClient(configKey = "barista-service")
public interface BaristaGateway {

    @POST
    public UUID brewCoffeeRequest(CoffeeBrewRequest request);

    @GET
    @Path("{brewReceipt}")
    public String checkStatus(@PathParam("brewReceipt") UUID brewReceipt);

}
