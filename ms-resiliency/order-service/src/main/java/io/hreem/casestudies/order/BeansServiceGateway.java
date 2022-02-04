package io.hreem.casestudies.order;

import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/beans")
@RegisterRestClient(configKey = "beans-service")
public interface BeansServiceGateway {

    @POST
    public UUID reserveBeansForOrder(BeansReservationRequest request);

}
