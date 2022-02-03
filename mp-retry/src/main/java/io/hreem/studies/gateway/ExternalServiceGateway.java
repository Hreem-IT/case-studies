package io.hreem.studies.gateway;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@RegisterRestClient(configKey = "external-service")
@RegisterProvider(RetriableResponseExceptionMapper.class)
public interface ExternalServiceGateway {

    @GET
    @Path("/404")
    @Retry(retryOn = RetriableResponseException.class, maxRetries = 3, maxDuration = 3000)
    String getGreeting404();

    @GET
    @Path("/500")
    @Retry(retryOn = RetriableResponseException.class, maxRetries = 3, maxDuration = 3000)
    String getGreeting500();

}
