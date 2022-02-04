package io.hreem.studies;

import io.hreem.studies.gateway.ExternalServiceGateway;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ScheduledTester {

    @Inject
    @RestClient
    ExternalServiceGateway gateway;

    @Scheduled(every = "5s")
    public void pingGreeting400() {
        try {
            gateway.getGreeting400();
        } catch (Exception e) {
            Log.info("Call failed");
        }
    }

    //@Scheduled(every = "5s")
    public void pingGreeting404() {
        try {
            gateway.getGreeting404();
        } catch (Exception e) {
            Log.info("Call failed");
        }
    }

    //@Scheduled(every = "5s")
    public void pingGreeting500() {
        try {
            gateway.getGreeting500();
        } catch (Exception e) {
            Log.info("Call failed");
        }
    }


}
