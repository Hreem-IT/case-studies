package io.hreem.casestudies.order.gateway;

import java.util.UUID;

public record BrewReceipt(UUID orderNumber, UUID brewRequestId) {

    public BrewReceipt forOrderNumber(UUID orderNumber) {
        return new BrewReceipt(orderNumber, this.brewRequestId);
    }
}
