package io.hreem.casestudies.bff;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.ServiceUnavailableException;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/orders")
@ApplicationScoped
@RegisterRestClient(configKey = "order-service")
public interface OrderGateway {

    @POST
    @Timeout
    @Fallback(fallbackMethod = "createOrderFallback")
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 30, delayUnit = ChronoUnit.SECONDS)
    public UUID createOrder(OrderRequest request);

    public default UUID createOrderFallback(OrderRequest request) {
        throw new ServiceUnavailableException("Order service is unavailable");
    }

    @GET
    public OrderInformation getOrderInformations();

}
