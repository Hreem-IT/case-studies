package io.hreem.casestudies.ms.testing.customer.order.model;

import java.util.UUID;

public record Order(UUID orderNumber, UUID productId, int quantity) {

}
