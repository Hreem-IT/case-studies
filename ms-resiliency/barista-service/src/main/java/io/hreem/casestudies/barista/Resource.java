package io.hreem.casestudies.barista;

import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.hreem.casestudies.barista.BrewStatus.Status;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;

@Path("/barista")
public class Resource {

    @Inject
    BrewsStore brewsStore;

    @POST
    public UUID requestBrew(@Valid BrewRequest brewRequest) {
        Log.info("Requesting brew for orderNr: " + brewRequest.orderNumber());
        final var pendingBrew = Brew.fromRequest(brewRequest);
        brewsStore.addOrder(pendingBrew);
        return pendingBrew.brewRequestId();
    }

    @GET
    @Path("{brewReceipt}")
    public String getBrewStatus(@PathParam("brewReceipt") UUID brewReceipt) {
        final var brew = brewsStore.getBrew(brewReceipt);
        return brew.status().toString();
    }

    @Scheduled(every = "3s")
    public void brewCoffee() {
        // Simulate coffee brewing
        final var optionalPendingBrew = brewsStore.getBrews().stream()
                .filter(predicate -> predicate.status() == BrewStatus.Status.BREWING)
                .findFirst();

        if (optionalPendingBrew.isEmpty())
            return;

        final var pendingBrew = optionalPendingBrew.get();
        final var finishedBrew = pendingBrew.withOrderStatus(Status.FINISHED);
        Log.info("Brewing for orderNr: " + pendingBrew.orderNumber());
        brewsStore.updateBrew(finishedBrew);
        return;
    }
}