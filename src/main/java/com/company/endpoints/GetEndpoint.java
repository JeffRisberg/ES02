package com.company.endpoints;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetSocketAddress;

/**
 * @author Jeff Risberg
 * @since 12/23/17
 */
@Singleton
@Path("get")
public class GetEndpoint {

  String clusterName = "elasticsearch";
  String indexName = "product";

  protected Client client;

  @Inject
  public GetEndpoint() {

    Settings settings = Settings.builder()
      .put("cluster.name", clusterName).build();

    this.client = new PreBuiltTransportClient(settings)
      .addTransportAddress(new TransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOne(@PathParam("id") String id) {
    GetResponse response = client.prepareGet(indexName, "default", id).get();

    String responseString = response.getSourceAsString();

    return Response.status(Response.Status.OK).entity(responseString).build();
  }
}
