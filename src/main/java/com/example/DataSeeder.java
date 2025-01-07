package com.example;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import java.io.IOException;

public class DataSeeder {
    private final ElasticsearchService esService;

    public DataSeeder(ElasticsearchService esService) {
        this.esService = esService;
    }

    public void seedProducts() throws IOException {
        Product product1 = new Product("1", "gaming laptop", "electronics", 1200.99);
        Product product2 = new Product("2", "office chair", "furniture", 150.50);
        Product product3 = new Product("3", "laptop", "electronics", 1201);

        indexProduct(product1);
        indexProduct(product2);
        indexProduct(product3);
    }

    private void indexProduct(Product product) throws IOException {
        IndexResponse response = esService.getClient()
            .index(i -> i.index("products")
                         .id(product.getId())
                         .document(product));
        System.out.println("added product " + response.id());
    }
}