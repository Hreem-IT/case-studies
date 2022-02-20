package io.hreem.casestudies.order;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.logging.Log;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Resource {

    @Inject
    Service orderService;

    @POST
    public Response createOrder(@Valid OrderRequest orderRequest) {
        Log.infof("Creating order for %s with order %s", orderRequest.customerName(), orderRequest.sku());
        final var response = orderService.createOrder(orderRequest);
        return Response.ok(response).build();
    }

    @GET
    public Response getOrdersInformation() {
        final var response = orderService.getOrdersInformation();
        return Response.ok(response).build();
    }
}