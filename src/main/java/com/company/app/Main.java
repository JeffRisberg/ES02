package com.company.app;

import com.company.service.CountService;
import com.company.service.DataService;
import com.company.service.DeleteService;
import com.company.service.IngestService;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String clusterName = "elasticsearch";

        Settings settings = Settings.builder()
                .put("cluster.name", clusterName).build();

        Client client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(new InetSocketAddress("127.0.0.1", 9300)));

        CountService countService = new CountService(client);
        DataService dataService = new DataService(client);
        IngestService ingestService = new IngestService(client);
        DeleteService deleteService = new DeleteService(client);

        // Count
        System.out.println("getMatchAllQueryCount " + countService.getMatchAllQueryCount());
        System.out.println("getBoolQueryCount " + countService.getBoolQueryCount());
        System.out.println("getPhraseQueryCount " + countService.getPhraseQueryCount());

        // Data
        System.out.println("getMatchAllQueryData ");
        dataService.getMatchAllQueryData().forEach(item -> System.out.println(item));

        System.out.println("getBoolQueryData ");
        dataService.getBoolQueryData().forEach(item -> System.out.println(item));

        System.out.println("getPhraseQueryData ");
        dataService.getPhraseQueryData().forEach(item -> System.out.println(item));

        //Ingest
        String json1 = "{" +
                "\"name\":\"skyji\"," +
                "\"job\":\"Admin\"," +
                "\"location\":\"India\"" +
                "}";

        String json2 = "{" +
                "\"name\":\"jom\"," +
                "\"job\":\"assiant\"," +
                "\"location\":\"Meana\"" +
                "}";
        // ingest single record
        System.out.println("\nIngestService response::: " +ingestService.ingest("tweet",json1) );

        // ingest batch of records
        System.out.println("\nIngestService response::: " + ingestService.ingest("tweet", Arrays.asList(json1, json2)));

        // Delete
        // delete one record by id
        //System.out.println("delete by id " + deleteService.delete("AVSMh1LBWlqOklhqtVNs"));
        //delete record by query
        System.out.println("delete by query " + deleteService.deleteByQuery("satendra"));

        client.close();
    }
}
