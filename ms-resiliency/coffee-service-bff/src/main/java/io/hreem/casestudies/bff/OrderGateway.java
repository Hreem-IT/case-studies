package io.hreem.casestudies.bff;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/orders")
@ApplicationScoped
@RegisterRestClient(configKey = "order-service")
public interface OrderGateway {

    @POST
    public UUID createOrder(OrderRequest request);

    @GET
    public OrderInformation getOrderInformations();

}
