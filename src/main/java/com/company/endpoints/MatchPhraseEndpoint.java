package com.company.endpoints;

import com.company.common.ISearch;
import com.company.common.SearchQuery;
import com.company.common.SearchQueryClause;
import com.company.config.ResourceLocator;
import com.company.es.ESSearchImpl;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.simple.JSONArray;
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
@Path("matchPhrase")
public class MatchPhraseEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public MatchPhraseEndpoint() {
    indexes.add("products");
  }

  @GET
  @Path("{fieldName}/{text}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response matchQuery(@PathParam("fieldName") String fieldName, @PathParam("text") String text) {

    String hostName = ResourceLocator.getSearchHost();
    int port = ResourceLocator.getSearchPort();
    String clusterName = ResourceLocator.getSearchClusterName();

    ISearch search = new ESSearchImpl(hostName, port, clusterName, indexes);

    SearchQueryClause clause1 = new SearchQueryClause(SearchQueryClause.ClauseType.MATCH_PHRASE, fieldName, text);

    JSONArray result = new JSONArray();

    search.search(SearchQuery.builder().clause(clause1).build(), item -> {
      JSONObject hitJSON = new JSONObject();

      hitJSON.put("id", item.getId());
      hitJSON.put("score", item.getScore());
      hitJSON.put("content", item.getSourceAsString());
      hitJSON.put("type", item.getType());

      result.add(hitJSON);
    });

    search.destroy();

    return Response.status(Response.Status.OK).entity(result).build();
  }
}
