package com.company.endpoints;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author Jeff Risberg
 * @since 12/23/17
 */
@Singleton
@Path("query")
public class QueryEndpoint {

    @Inject
    public QueryEndpoint() {
    }

    @GET
    @Path("{query}")
    public Response handle(@PathParam("query") String query) {
        Object results = query;
        return Response.status(Response.Status.OK).entity(results).build();
    }
}
