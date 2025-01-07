package com.example;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

import java.io.IOException;
import java.util.List;

public class ProductSearch {
    private final ElasticsearchService esService;

    public ProductSearch(ElasticsearchService esService) {
        this.esService = esService;
    }

    public void nameFieldSearch(String query) throws IOException {
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

    public void multiFieldSearch(String query) throws IOException {
        SearchResponse<Product> response = esService.getClient()
                .search(s -> s.index("products")
                        .query(q -> q.bool(b -> b
                                .should(sh -> sh.match(m -> m.field("name").query(query)))
                                .should(sh -> sh.match(m -> m.field("category").query(query)))
                        )),
                        Product.class);

        List<Hit<Product>> hits = response.hits().hits();
        hits.forEach(hit -> {
            Product product = hit.source();
            if (product != null) {
                System.out.println("found product: " + product.getName());
                System.out.println("category: " + product.getCategory());
                System.out.println("price: " + product.getPrice());
            }
        });
    }

    public void listAllProducts() throws IOException {
        SearchResponse<Product> response = esService.getClient()
                .search(s -> s.index("products")
                              .query(q -> q.matchAll(m -> m)) 
                              .size(1000), 
                        Product.class);

        List<Hit<Product>> hits = response.hits().hits();
        if (hits.isEmpty()) {
            System.out.println("no products found in the index");
        } else {
            System.out.println("all products:");
            hits.forEach(hit -> {
                Product product = hit.source();
                if (product != null) {
                    System.out.println("id: " + product.getId());
                    System.out.println("name: " + product.getName());
                    System.out.println("category: " + product.getCategory());
                    System.out.println("price: " + product.getPrice());
                    System.out.println("-------------");
                }
            });
        }
    }
}