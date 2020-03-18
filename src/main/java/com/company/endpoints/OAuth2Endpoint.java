package com.company.endpoints;

import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Jeff Risberg
 * @since 11/30/17
 */
@Singleton
@Path("/tenants/{tenantId}")
public class OAuth2Endpoint {

  @Inject
  public OAuth2Endpoint() {
  }

  @POST
  @Path("/test1")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response test1(@PathParam("tenantId") String tenantId,
                        @FormParam("alpha") String alpha,
                        @FormParam("beta") String beta) {
    JSONObject result = new JSONObject();
    Object results = "Test1 " + tenantId + " " + alpha + " " + beta;

    return Response.status(Response.Status.OK).entity(results).build();
  }

  @POST
  @Path("/test2")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response test2(@PathParam("tenantId") String tenantId,
                        @FormParam("alpha") String alpha,
                        @FormParam("beta") String beta) {
    JSONObject result = new JSONObject();


    Object results = "Test2 " + tenantId + " " + alpha + " " + beta;

    return Response.status(Response.Status.OK).entity(results).build();
  }
}
