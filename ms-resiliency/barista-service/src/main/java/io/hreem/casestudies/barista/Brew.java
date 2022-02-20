package io.hreem.casestudies.barista;

import java.util.UUID;

public record Brew(
                UUID brewRequestId,
                UUID orderNumber,
                UUID beansReservationNumber,
                String customerName,
                String sku,
                int quantity,
                BrewStatus.Status status) {

        public static Brew fromRequest(BrewRequest request) {
                return new Brew(
                                UUID.randomUUID(),
                                request.orderNumber(),
                                request.beansReservationNumber(),
                                request.customerName(),
                                request.sku(),
                                request.quantity(),
                                BrewStatus.Status.BREWING);
        }

        public Brew withOrderStatus(BrewStatus.Status status) {
                return new Brew(
                                this.brewRequestId,
                                this.orderNumber,
                                this.beansReservationNumber,
                                this.customerName,
                                this.sku,
                                this.quantity,
                                status);
        }
}