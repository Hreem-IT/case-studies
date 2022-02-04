package io.hreem.casestudies.beans;

import java.util.UUID;

public record BeansReservationRequest(
        UUID orderNumber,
        String sku) {

}
