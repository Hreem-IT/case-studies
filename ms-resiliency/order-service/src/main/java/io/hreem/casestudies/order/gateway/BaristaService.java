package io.hreem.casestudies.order.gateway;

import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.logging.Log;

@Dependent
public class BaristaService {

    @Inject
    @RestClient
    BaristaGateway baristaGateway;

    public BrewReceipt brewCoffee(CoffeeBrewRequest request) {
        final var brewReceipt = baristaGateway.brewCoffeeRequest(request);
        return new BrewReceipt(request.orderNumber(), brewReceipt);
    }

    public BrewStatus checkBrewStatus(UUID orderNumber, UUID brewRequestId) {
        final var statusResponse = baristaGateway.checkStatus(brewRequestId);
        return new BrewStatus(orderNumber, BrewStatus.Status.valueOf(statusResponse));
    }
}
