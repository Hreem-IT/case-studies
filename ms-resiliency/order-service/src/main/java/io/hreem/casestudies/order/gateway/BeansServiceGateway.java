package io.hreem.casestudies.order.gateway;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/beans")
@RegisterRestClient(configKey = "beans-service")
public interface BeansServiceGateway {

    @POST
    @Retry(maxRetries = 3, maxDuration = 4)
    public Response reserveBeansForOrder(BeansReservationRequest request);

}
