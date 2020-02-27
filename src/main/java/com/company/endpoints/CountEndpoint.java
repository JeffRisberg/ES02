package com.company.endpoints;

import com.company.common.ISearch;
import com.company.common.SearchQuery;
import com.company.common.SearchQueryClause;
import com.company.config.PropertyManager;
import com.company.es.ESSearchImpl;

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
@Path("count")
public class CountEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public CountEndpoint() {
    indexes.add("products");
  }

  @GET
  @Path("{fieldName}/{text}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response count(@PathParam("fieldName") String fieldName, @PathParam("text") String text) {

    String hostName = PropertyManager.getSearchHost();
    int port = PropertyManager.getSearchPort();
    String clusterName = PropertyManager.getSearchClusterName();

    ISearch search = new ESSearchImpl(hostName, port, clusterName, indexes);

    SearchQueryClause clause1 = new SearchQueryClause(SearchQueryClause.ClauseType.MATCH, fieldName, text);

    Long result = search.count(SearchQuery.builder().clause(clause1).build());

    search.destroy();

    return Response.status(Response.Status.OK).entity(result).build();
  }
}
