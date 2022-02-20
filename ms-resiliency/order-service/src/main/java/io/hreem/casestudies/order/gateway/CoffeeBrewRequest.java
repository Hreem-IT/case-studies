package io.hreem.casestudies.order.gateway;

import java.util.UUID;

public record CoffeeBrewRequest(
        UUID orderNumber,
        UUID beansReservationNumber,
        String customerName,
        String sku,
        int quantity) {
}
