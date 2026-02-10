package com.accenture.pip.product_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.accenture.pip.product_service.entity.Product;
import com.accenture.pip.product_service.repository.ProductRepository;

@Configuration
public class DataInitializer {
	
	@Bean
    CommandLineRunner initProducts(ProductRepository productRepository) {
        return args -> {

            if (productRepository.count() == 0) {

                Product p1 = new Product(null, "iPhone 15", 79999.00);
                Product p2 = new Product(null, "MacBook Pro", 199999.00);

                productRepository.save(p1);
                productRepository.save(p2);
            }
        };
    }

}
