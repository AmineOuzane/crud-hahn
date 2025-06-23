package org.sid.crudcandidaturehahn;

import org.sid.crudcandidaturehahn.dto.ProductDTO;
import org.sid.crudcandidaturehahn.repositories.ProductRepository;
import org.sid.crudcandidaturehahn.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableCaching
public class CrudCandidatureHahnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudCandidatureHahnApplication.class, args);
    }

    // CommandLineRunner to create sample products for testing
    //@Bean
    public CommandLineRunner dataLoader(ProductService produitService) {
        return args -> {
            produitService.createProduct(
                ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Product 1")
                    .description("Description for Product 1")
                    .price(100.0)
                    .createdAt(LocalDateTime.now())
                    .build()
            );
            produitService.createProduct(
                    ProductDTO.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Product 2")
                            .description("Description for Product 2")
                            .price(200.0)
                            .createdAt(LocalDateTime.now())
                            .build()
            );
            produitService.createProduct(
                    ProductDTO.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Product 3")
                            .description("Description for Product 3")
                            .price(450.0)
                            .createdAt(LocalDateTime.now())
                            .build()
            );
        };
    }
}
