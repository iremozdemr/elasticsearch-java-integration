package com.example;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import java.io.IOException;

public class ProductSearch {
    private final ElasticsearchService esService;

    public ProductSearch(ElasticsearchService esService) {
        this.esService = esService;
    }

    public void searchProducts(String query) throws IOException {
        SearchResponse<Product> response = esService.getClient()
                .search(s -> s.index("products")
                              .query(q -> q.match(t -> t.field("name").query(query))),
                        Product.class);

        response.hits().hits().forEach(hit -> {
            Product product = hit.source();
            if (product != null) {
                System.out.println("found product:" + product.getName());
                System.out.println("price:" + product.getPrice());
            }
        });
    }
}