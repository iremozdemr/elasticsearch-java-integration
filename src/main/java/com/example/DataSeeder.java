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

    public void updateProductPrice(String productId, double newPrice) throws IOException {
        esService.getClient().update(u -> u
            .index("products")
            .id(productId)
            .doc(new Product(productId, null, null, newPrice)),
            Product.class
        );
        System.out.println("updated product price for id: " + productId);
    }

    public void updateProductCategory(String productId, String newCategory) throws IOException {
        esService.getClient().update(u -> u
            .index("products")
            .id(productId)
            .doc(new Product(productId, null, newCategory, 0)),
            Product.class
        );
        System.out.println("updated product category for id: " + productId);
    }

    public void deleteProduct(String productId) throws IOException {
        esService.getClient().delete(d -> d
            .index("products")
            .id(productId)
        );
        System.out.println("deleted product with id: " + productId);
    }  
}