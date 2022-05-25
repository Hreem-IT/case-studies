package io.hreem.casestudies.ms.testing.customer.order.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.hreem.casestudies.ms.testing.customer.order.domain.CustomerOrderService;
import io.hreem.casestudies.ms.testing.customer.order.model.Order;

@Path("customers/{id}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerOrderResource {

    @Inject
    CustomerOrderService customerOrderService;

    @POST
    public Response registerOrderForCustomer(@PathParam("id") String customerId, Order order) {
        final var response = customerOrderService.registerOrderForCustomer(customerId, order);
        return Response.ok(response).build();
    }
}