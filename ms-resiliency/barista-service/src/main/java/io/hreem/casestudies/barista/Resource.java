package io.hreem.casestudies.barista;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    Random random = new Random();

    @POST
    public UUID requestBrew(@Valid BrewRequest brewRequest) throws InterruptedException {
        // Add a random delay to simulate an unhealthy service
        final var rnd = random.nextInt(2);
        if (rnd == 0)
            Thread.sleep(Duration.ofSeconds(30).toMillis());

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

    @Scheduled(every = "0.01s")
    public void brewCoffee() {
        final var pendingBrews = brewsStore.getBrews().stream()
                .filter(predicate -> predicate.status() == BrewStatus.Status.BREWING)
                .collect(Collectors.toList());

        if (pendingBrews.isEmpty())
            return;

        // Simulate brewing
        pendingBrews.stream().forEach(pendingBrew -> {
            final var finishedBrew = pendingBrew.withOrderStatus(Status.FINISHED);
            Log.info("Brewing for orderNr: " + pendingBrew.orderNumber());
            brewsStore.updateBrew(finishedBrew);
        });
        return;
    }
}