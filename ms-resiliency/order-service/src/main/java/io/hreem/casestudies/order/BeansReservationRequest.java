package io.hreem.casestudies.order;

import java.util.UUID;

public record BeansReservationRequest(
        UUID orderNumber,
        String sku) {
}
