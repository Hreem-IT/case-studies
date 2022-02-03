package io.hreem.studies;

import io.hreem.studies.gateway.ExternalServiceGateway;
import io.quarkus.test.junit.QuarkusTest;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class ExternalServiceGatewayTest {

    @Inject
    @RestClient
    ExternalServiceGateway externalServiceGateway;

    @Test
    public void testGatewayCall404() {
        try {
            externalServiceGateway.getGreeting404();
        } catch (Exception e) {
        }

    }

    @Test
    public void testGatewayCall500() {
        try {
            externalServiceGateway.getGreeting500();
        } catch (Exception e) {
        }
    }

}