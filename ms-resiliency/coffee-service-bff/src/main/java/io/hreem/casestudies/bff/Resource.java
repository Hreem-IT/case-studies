package io.hreem.casestudies.bff;

import javax.inject.Inject;
import javax.naming.ServiceUnavailableException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class Resource {

    @Inject
    @RestClient
    BeanGateway beanGateway;

    @Inject
    @RestClient
    OrderGateway orderGateway;

    @GET
    @Path("/beans")
    public Response getBeansInventory() {
        return Response.ok(beanGateway.getBeans()).build();
    }

    @POST
    @Path("/orders")
    public Response createOrder(OrderRequest request) {
        return Response
                .ok(orderGateway.createOrder(request))
                .build();
    }

    @GET
    @Path("/orders")
    public Response getOrdersInformation() {
        return Response
                .ok(orderGateway.getOrderInformations())
                .build();
    }
}