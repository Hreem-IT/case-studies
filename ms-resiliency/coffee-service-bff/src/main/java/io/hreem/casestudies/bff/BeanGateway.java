package io.hreem.casestudies.bff;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/beans")
@ApplicationScoped
@RegisterRestClient(configKey = "beans-service")
public interface BeanGateway {

    @GET
    public float getBeans();

}
