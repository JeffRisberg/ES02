package com.company.endpoints;

import com.company.common.ISearch;
import com.company.common.SearchQuery;
import com.company.common.SearchQueryClause;
import com.company.es.ESSearchImpl;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 12/23/17
 */
@Singleton
@Path("count")
public class CountEndpoint {
  @Inject
  public CountEndpoint() {

    System.out.println("creating CountEndpoint");
  }

  @GET
  @Path("/{fieldName}/{text}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response count(@PathParam("fieldName") String fieldName, @PathParam("text") String text) {
    System.out.println("invoke count");

    List<String> indexes = new ArrayList<>();
    indexes.add("products");

    ISearch search = new ESSearchImpl("127.0.0.1", 9300, "elasticsearch", indexes);

    SearchQueryClause clause1 = new SearchQueryClause(SearchQueryClause.ClauseType.TERM, fieldName, text);

    Long result = search.count(SearchQuery.builder().clause(clause1).build());

    search.destroy();

    return Response.status(Response.Status.OK).entity(result).build();
  }
}
