package io.hreem.casestudies.order;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
        final var response = orderService.createOrder(orderRequest);
        return Response.ok(response).build();
    }
}