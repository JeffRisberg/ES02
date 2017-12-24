package com.company.service;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import java.util.List;

public class IngestService {

    Client client;

    public IngestService(Client client) {
        this.client = client;
    }

    public IndexResponse ingest(String type, String doc) {
        return client.prepareIndex("es01", type).setSource(doc).get();
    }

    public boolean ingest(String type, List<String> docs) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        docs.forEach(doc -> bulkRequest.add(client.prepareIndex("es01", type).setSource(doc)));

        return bulkRequest.get().hasFailures();
    }
}
