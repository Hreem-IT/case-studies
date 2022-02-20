package io.hreem.casestudies.barista;

import java.util.UUID;

public record BrewRequest(
        UUID orderNumber,
        UUID beansReservationNumber,
        String customerName,
        String sku,
        int quantity) {

}