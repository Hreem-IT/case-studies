package io.hreem.casestudies.order;

import java.util.UUID;

public record Order(
        UUID orderNumber,
        UUID beansReservationNumber,
        String customerName,
        String sku,
        int quantity,
        Status orderStatus,
        UUID brewRequestId) {

    public enum Status {
        PENDING,
        PROCESSING,
        COMPLETED
    }

    public Order withOrderStatus(Status processing) {
        return new Order(
                this.orderNumber,
                this.beansReservationNumber,
                this.customerName,
                this.sku,
                this.quantity,
                processing,
                this.brewRequestId);
    }

    public Order withBrewRequestId(UUID brewRequestId, Status processing) {
        return new Order(
                this.orderNumber,
                this.beansReservationNumber,
                this.customerName,
                this.sku,
                this.quantity,
                processing,
                brewRequestId);
    }
}
