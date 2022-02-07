package io.hreem.casestudies.order;

import java.util.UUID;

public class Order {

    public enum Status {
        CREATED,
        PROCESSING,
        COMPLETED,
        CANCELLED
    }

    UUID orderNumber;
    UUID beansReservationNumber;
    String customerName;
    String sku;
    int quantity;
    Status orderStatus = Status.CREATED;

}
