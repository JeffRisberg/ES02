package com.company.service;

import com.company.common.SearchQuery;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.function.Consumer;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class SearchService {

  Client client;

  public SearchService(Client client) {
    this.client = client;
  }

  /**
   * @param query    query description
   * @param consumer object to consume search result
   */
  void searchFulltext(SearchQuery query, Consumer<SearchResultRecord> consumer) {
    QueryBuilder thisQuery = matchAllQuery();
    System.out.println("getMatchAllQueryCount query =>" + query.toString());
    SearchHit[] hits = client.prepareSearch("products").setQuery(thisQuery).execute().actionGet().getHits().getHits();

    for (SearchHit hit : hits) {
      // create SearchResultRecord
      // send to consumer
    }
  }
}
