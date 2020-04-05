package com.company.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 12/23/17
 */
@Slf4j
@Singleton
@Path("populate")
public class PopulateEndpoint {

  protected List<String> indexes = new ArrayList<>();

  @Inject
  public PopulateEndpoint() {
    indexes.add("articles");
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response count() {
    JSONParser parser = new JSONParser();

    String indexName = "articles";

    try {
      RestClient restClient = RestClient.builder(
        new HttpHost("localhost", 9200, "http"),
        new HttpHost("localhost", 9201, "http")).build();

      // create index
      //UUID uuid = UUID.randomUUID();
      //String indexName = "articles-" + uuid.toString();

      Request ciRequest = new Request("PUT", "/" + indexName);
      org.elasticsearch.client.Response ciResponse = restClient.performRequest(ciRequest);
      log.info(ciResponse.toString());

      // populate index
      List<String> lines = Files.readAllLines(Paths.get("article-data.json"), StandardCharsets.UTF_8);

      StringBuilder sb = new StringBuilder(1024);
      for (String line : lines) {
        sb.append(line);
      }
      JSONArray articlesArray = (JSONArray) parser.parse(sb.toString());

      for (Object object : articlesArray) {
        JSONObject article = (JSONObject) object;

        /**
         * You can index a new JSON document with the _doc or _create resource. Using _create guarantees that
         * the document is only indexed if it does not already exist.
         * To update an existing document, you must use the _doc resource.
         */
        String type = "_doc";

        Request indexRequest = new Request("POST", "/" + indexName + "/" + type + "?refresh=wait_for");
        indexRequest.setJsonEntity(((JSONObject) article).toJSONString());

        org.elasticsearch.client.Response indexResponse = restClient.performRequest(indexRequest);
        log.info(indexResponse.toString());
      }
    } catch (Exception e) {
      log.error("", e);
    }

    return Response.status(Response.Status.OK).entity(indexName).build();
  }
}
