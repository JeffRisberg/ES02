package com.company.endpoints;

import com.company.common.ISearch;
import com.company.config.PropertyManager;
import com.company.es.ESSearchImpl;
import org.elasticsearch.action.index.IndexResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
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
@Path("ingest")
public class IngestArticleEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public IngestArticleEndpoint() {
    indexes.add("articles");
  }

  @POST
  @Path("{indexName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOne(@PathParam("indexName") String indexName, String body) {

    String hostName = PropertyManager.getSearchHost();
    int port = PropertyManager.getSearchPort();
    String clusterName = PropertyManager.getSearchClusterName();

    ISearch search = new ESSearchImpl(hostName, port, clusterName, indexes);

    IndexResponse response = search.ingest(indexName, "Default", body);

    search.destroy();

    return Response.status(Response.Status.OK).entity(response).build();
  }
}
