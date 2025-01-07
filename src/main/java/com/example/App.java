package com.example;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        ElasticsearchService esService = new ElasticsearchService();
        DataSeeder dataSeeder = new DataSeeder(esService);
        ProductSearch productSearch = new ProductSearch(esService);

        esService.ping();

        dataSeeder.seedProducts();

        productSearch.listAllProducts();

        dataSeeder.updateProductPrice("1", 1300.50);

        dataSeeder.updateProductCategory("2", "office supplies");

        dataSeeder.deleteProduct("3");

        productSearch.listAllProducts();

        System.out.println("-- searching by name --");
        productSearch.nameFieldSearch("laptop");

        System.out.println("-- searching in multiple fields --");
        productSearch.multiFieldSearch("electronics");

        esService.close();
    }
}