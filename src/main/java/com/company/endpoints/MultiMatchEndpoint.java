package com.company.endpoints;

import com.company.common.ISearch;
import com.company.common.SearchQuery;
import com.company.common.SearchQueryClause;
import com.company.config.PropertyManager;
import com.company.es.ESSearchImpl;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 12/23/17
 */
@Singleton
@Path("multiMatch")
public class MultiMatchEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public MultiMatchEndpoint() {
    indexes.add("products");
  }

  @GET
  @Path("{fieldNames}/{text}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response multiMatchQuery(@PathParam("fieldNames") String fieldNames, @PathParam("text") String text) {

    String hostName = PropertyManager.getSearchHost();
    int port = PropertyManager.getSearchPort();
    String clusterName = PropertyManager.getSearchClusterName();

    ISearch search = new ESSearchImpl(hostName, port, clusterName, indexes);

    SearchQueryClause clause1 = new SearchQueryClause(SearchQueryClause.ClauseType.MULTI_MATCH, fieldNames, text);

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
