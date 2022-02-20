package io.hreem.casestudies.order.gateway;

import java.util.UUID;

public record BeansReservationRequest(
        UUID orderNumber,
        String sku) {
}
