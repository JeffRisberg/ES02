package com.company.endpoints;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
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
@Path("multiMatch")
public class MultiMatchEndpoint {

  String clusterName = "elasticsearch";
  String indexName = "products";

  protected Client client;

  @Inject
  public MultiMatchEndpoint() {

    Settings settings = Settings.builder()
      .put("cluster.name", clusterName).build();

    this.client = new PreBuiltTransportClient(settings)
      .addTransportAddress(new TransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
  }

  @GET
  @Path("{query}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response matchQuery(@PathParam("query") String queryStr) {
    QueryBuilder query = QueryBuilders.multiMatchQuery(queryStr, "name", "descrption", "tags");
    System.out.println("search query => " + query.toString());

    SearchHit[] hits = client.prepareSearch(indexName).setQuery(query).execute().actionGet().getHits().getHits();

    List<JSONObject> list = new ArrayList<JSONObject>();
    for (SearchHit hit : hits) {
      JSONObject hitJSON = new JSONObject();

      hitJSON.put("_id", hit.getId());
      hitJSON.put("_score", hit.getScore());
      hitJSON.put("_index", hit.getIndex());
      hitJSON.put("_type", hit.getType());
      hitJSON.put("_source", hit.getSourceAsMap());

      list.add(hitJSON);
    }

    return Response.status(Response.Status.OK).entity(list).build();
  }
}
