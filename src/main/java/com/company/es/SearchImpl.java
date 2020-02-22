package com.company.es;

import com.company.common.ISearch;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

public class SearchImpl implements ISearch {

  protected Client client;

  @Override
  public void init() {
    String clusterName = "elasticsearch";
    String indexName = "products";

    Settings settings = Settings.builder()
      .put("cluster.name", clusterName).build();

    client = new PreBuiltTransportClient(settings)
      .addTransportAddress(new TransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
  }

  @Override
  public void destory() {
    if (client != null) {
      client.close();
    }
  }
}
