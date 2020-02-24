package com.company.endpoints;

import com.company.common.ISearch;
import com.company.common.SearchResult;
import com.company.config.ResourceLocator;
import com.company.es.ESSearchImpl;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 12/23/17
 */
@Singleton
@Path("get")
public class GetEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public GetEndpoint() {
    indexes.add("products");
  }

  @GET
  @Path("{indexName}/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOne(@PathParam("indexName") String indexName, @PathParam("id") String id) {

    String hostName = ResourceLocator.getSearchHost();
    int port = ResourceLocator.getSearchPort();
    String clusterName = ResourceLocator.getSearchClusterName();

    ISearch search = new ESSearchImpl(hostName, port, clusterName, indexes);

    SearchResult item = search.get(indexName, id);

    JSONObject hitJSON = new JSONObject();

    hitJSON.put("id", item.getId());
    hitJSON.put("score", item.getScore());
    hitJSON.put("content", item.getSourceAsString());
    hitJSON.put("type", item.getType());

    search.destroy();

    return Response.status(Response.Status.OK).entity(hitJSON).build();
  }
}
