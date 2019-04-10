package com.company.service;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;

/***
 * add delete by query plugin to Elastisearch
 * $ bin/plugin install delete-by-query
 */
public class DeleteService {
  Client client;

  public DeleteService(Client client) {
    this.client = client;
  }

  public DeleteResponse delete(String id) {
    return client.prepareDelete("test", "tweet", id).get();
  }

  public long deleteByQuery(String name) {
        /*
        return new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE).setQuery(QueryBuilders.termQuery("name", name))
                .execute().actionGet().getTotalDeleted();
                */
    return 0L;
  }
}
