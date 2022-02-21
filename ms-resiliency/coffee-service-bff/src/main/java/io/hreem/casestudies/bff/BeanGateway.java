package io.hreem.casestudies.bff;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.faulttolerance.Fallback;

@Path("/beans")
@ApplicationScoped
@CircuitBreaker(
    requestVolumeThreshold = 10, 
    failureRatio = 0.5, 
    successThreshold = 5, 
    delay = 30, 
    delayUnit = ChronoUnit.SECONDS)
@RegisterRestClient(configKey = "beans-service")
public interface BeanGateway {

    @GET
    @Fallback(fallbackMethod = "getBeansFallback")
    public float getBeans();

    public default float getBeansFallback() {
        throw new javax.ws.rs.ServiceUnavailableException();
    }

}
