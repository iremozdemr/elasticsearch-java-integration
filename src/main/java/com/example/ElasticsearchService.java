package com.example;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import java.io.IOException;

public class ElasticsearchService {
    private final ElasticsearchClient esClient;
    private final RestClient restClient;

    public ElasticsearchService() {
        String serverUrl = "";
        String apiKey = "";

        restClient = RestClient.builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new org.apache.http.Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        esClient = new ElasticsearchClient(transport);
    }

    public void ping() throws IOException {
        boolean pingResult = esClient.ping().value();
        if(pingResult){
            System.out.println("connected to elasticsearch");
        }
        else{
            System.out.println("not connected to elasticsearch");
        }
    }

    public ElasticsearchClient getClient() {
        return esClient;
    }

    public void close() throws IOException {
        restClient.close();
    }
}