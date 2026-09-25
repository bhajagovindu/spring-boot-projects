package com.example._4ecommerceapi;

import com.example._4ecommerceapi.model.Category;
import com.example._4ecommerceapi.model.Product;
import com.example._4ecommerceapi.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository repo;
    public DataLoader(ProductRepository r) { repo = r; }

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            save("iPhone 15 Pro",    "Apple flagship smartphone",        134999.0, 25,  Category.ELECTRONICS);
            save("Samsung Galaxy S24","Samsung flagship Android phone",   89999.0,  40,  Category.ELECTRONICS);
            save("Sony WH-1000XM5",  "Noise cancelling headphones",       29999.0,  60,  Category.ELECTRONICS);
            save("Nike Air Max 270",  "Men's lifestyle running shoes",     12999.0,  100, Category.SPORTS);
            save("Levi's 511 Jeans",  "Classic slim fit jeans",            4999.0,   200, Category.CLOTHING);
            save("Clean Code Book",   "By Robert C. Martin",              799.0,    75,  Category.BOOKS);
            save("MacBook Air M2",    "Apple laptop with M2 chip",        114900.0, 15,  Category.ELECTRONICS);
            save("Yoga Mat Pro",      "Non-slip premium yoga mat",         1499.0,   150, Category.SPORTS);
            System.out.println("✅ Sample products loaded!");
        }
    }

    private void save(String name, String desc, Double price,
                      Integer stock, Category cat) {
        Product p = new Product();
        p.setName(name); p.setDescription(desc);
        p.setPrice(price); p.setStock(stock); p.setCategory(cat);
        repo.save(p);
    }
}