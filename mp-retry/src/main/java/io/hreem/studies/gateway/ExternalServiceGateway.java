package io.hreem.studies.gateway;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@RegisterRestClient(configKey = "external-service")
@RegisterProvider(RequestExceptionMapper.class)
@Retry(
        retryOn = RetryableRequestException.class,
        maxRetries = 4
)
@CircuitBreaker(
        successThreshold = 5,
        requestVolumeThreshold = 10,
        failureRatio = 0.5,
        delay = 30000,
        skipOn = {
                NotFoundRequestException.class,
                BadRequestException.class
        }
)
public interface ExternalServiceGateway {


    @GET
    @Path("/400")
    String getGreeting400();

    @GET
    @Path("/404")
    String getGreeting404();

    @GET
    @Path("/500")
    String getGreeting500();

}
