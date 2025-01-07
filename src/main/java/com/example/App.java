package com.example;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        ElasticsearchService esService = new ElasticsearchService();
        DataSeeder dataSeeder = new DataSeeder(esService);
        ProductSearch productSearch = new ProductSearch(esService);

        esService.ping();

        dataSeeder.seedProducts();

        productSearch.searchProducts("laptop");

        esService.close();
    }
}