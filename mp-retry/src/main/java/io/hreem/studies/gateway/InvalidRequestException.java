package io.hreem.studies.gateway;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InvalidRequestException extends WebApplicationException {
    public InvalidRequestException(Response response) {
        super(response);
    }
}
