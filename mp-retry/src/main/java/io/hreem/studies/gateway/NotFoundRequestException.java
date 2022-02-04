package io.hreem.studies.gateway;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotFoundRequestException extends WebApplicationException {
    public NotFoundRequestException(Response response) {
        super(response);
    }
}
