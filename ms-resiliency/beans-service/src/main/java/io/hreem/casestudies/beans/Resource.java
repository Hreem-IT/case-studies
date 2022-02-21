package io.hreem.casestudies.beans;

import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/beans")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Resource {

    @Inject
    Service beansService;

    @GET
    public Response getBeansInStock() {
        return Response.ok(beansService.getBeansStockGrams()).build();
    }

    @GET
    @Path("reservations")
    public Response getBeanReservations() {
        return Response.ok(beansService.getReservedBeans()).build();
    }

    @POST
    public Response reserveBeansForUpcomingOrder(@Valid BeansReservationRequest request) {
        // Random 500 error added to simulate a failure
        final var random = new Random();
        if (random.nextInt(2) == 0)
            return Response.serverError().build();
        return Response.ok(beansService.reserveBeansForUpcomingOrder(request)).build();
    }

    @DELETE
    public Response unreserveBeans(@PathParam("reservationNumber") UUID reservationNumber) {
        beansService.unreserveBeans(reservationNumber);
        return Response.ok().build();
    }
}