package io.hreem.casestudies.ms.testing.customer.order.gateway;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.hreem.casestudies.ms.testing.customer.order.model.PurchaseHistory;

@Path("loyalties/{customerId}")
@RegisterRestClient(baseUri = "http://localhost:8090")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LoyaltyServiceGateway {

    @PUT
    Response updateLoyaltyPoints(@PathParam("customerId") String customerId, PurchaseHistory purchaseHistory);

}
