package io.hreem.studies.gateway;

import javax.ws.rs.WebApplicationException;

public class RetriableResponseException extends WebApplicationException {
    public RetriableResponseException(int status) {
        super(status);
    }
}
