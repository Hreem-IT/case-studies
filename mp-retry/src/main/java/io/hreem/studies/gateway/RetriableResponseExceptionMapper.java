package io.hreem.studies.gateway;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class RetriableResponseExceptionMapper implements ResponseExceptionMapper<WebApplicationException> {

    private static final List<Integer> RETRYABLE_STATUS_CODES = Arrays.asList(
            // 400 type errors
            Response.Status.CONFLICT.getStatusCode(),
            Response.Status.REQUEST_TIMEOUT.getStatusCode(),
            Response.Status.TOO_MANY_REQUESTS.getStatusCode(),
            // 500 type errors
            Response.Status.BAD_GATEWAY.getStatusCode(),
            Response.Status.SERVICE_UNAVAILABLE.getStatusCode(),
            Response.Status.GATEWAY_TIMEOUT.getStatusCode(),
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

    @Override
    public WebApplicationException toThrowable(Response response) {
        if (RETRYABLE_STATUS_CODES.contains(response.getStatus())) {
            Log.info("Retriable response: " + response.getStatus());
            return new RetriableResponseException(response.getStatus());
        }
        return new WebApplicationException(response);
    }
}
