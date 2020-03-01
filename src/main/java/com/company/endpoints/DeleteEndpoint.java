package com.company.endpoints;

import com.company.common.ISearch;
import com.company.config.PropertyManager;
import com.company.es.ESSearchImpl;
import org.elasticsearch.action.delete.DeleteResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
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
public class DeleteEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public DeleteEndpoint() {
    indexes.add("products");
  }

  @DELETE
  @Path("{indexName}/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOne(@PathParam("indexName") String indexName, @PathParam("id") String id) {

    String hostName = PropertyManager.getSearchHost();
    int port = PropertyManager.getSearchPort();
    String clusterName = PropertyManager.getSearchClusterName();

    ISearch search = new ESSearchImpl(hostName, port, clusterName, indexes);

    DeleteResponse response = search.delete(indexName, id);

    search.destroy();

    return Response.status(Response.Status.OK).entity(response).build();
  }
}
