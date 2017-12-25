package com.company.endpoints;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
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
@Path("query")
public class QueryEndpoint {

    String clusterName = "elasticsearch";
    String indexName = "product";

    protected Client client;

    @Inject
    public QueryEndpoint() {

        Settings settings = Settings.builder()
                .put("cluster.name", clusterName).build();

        this.client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
    }

    @GET
    @Path("{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handle(@PathParam("query") String queryStr) {
        QueryBuilder query = QueryBuilders.matchAllQuery();
        System.out.println("getMatchAllQueryCount query => " + query.toString());

        SearchHit[] hits = client.prepareSearch(indexName).setQuery(query).execute().actionGet().getHits().getHits();

        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            list.add(hit.getSourceAsString());
        }

        GenericEntity<List<String>> entity = new GenericEntity<List<String>>(list) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }
}
