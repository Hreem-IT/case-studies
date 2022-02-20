package io.hreem.casestudies.order.gateway;

import java.util.UUID;

public record BrewStatus(UUID orderNumber, Status brewStatus) {
    public enum Status {
        BREWING,
        FINISHED;
    }

    public BrewStatus forOrderNumber(UUID orderNumber) {
        return new BrewStatus(orderNumber, brewStatus);
    }
}
