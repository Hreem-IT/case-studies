package io.hreem.casestudies.barista;

public record BrewStatus(Status brewStatus) {
    public enum Status {
        BREWING,
        FINISHED
    }
}
