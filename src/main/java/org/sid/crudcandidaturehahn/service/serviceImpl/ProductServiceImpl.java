package org.sid.crudcandidaturehahn.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.dto.ProductDTO;
import org.sid.crudcandidaturehahn.entities.Product;
import org.sid.crudcandidaturehahn.repositories.ProductRepository;
import org.sid.crudcandidaturehahn.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for managing products.
 * Provides methods to create, retrieve, update, and delete products.
 */
@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Override
    @CacheEvict(value = "productsAll", allEntries = true)
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            if (productDTO == null) {
                throw new IllegalArgumentException("ProductDTO cannot be null");
            }
            if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be null or empty");
            }
            if (productDTO.getPrice() <= 0) {
                throw new IllegalArgumentException("Product price must be greater than zero");
            }

            Product product = Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(productDTO.getName().trim())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .createdAt(LocalDateTime.now())
                    .build();

            Product savedProduct = productRepository.save(product);

            return ProductDTO.builder()
                    .id(savedProduct.getId())
                    .name(savedProduct.getName())
                    .description(savedProduct.getDescription())
                    .price(savedProduct.getPrice())
                    .createdAt(savedProduct.getCreatedAt())
                    .build();

        } catch (IllegalArgumentException e) {

             logger.error("Invalid product data for creation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
             logger.error("Error creating product", e);
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(String id) {

        return productRepository.findById(id)
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .createdAt(product.getCreatedAt())
                        .build())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    @Cacheable(value = "productsAll")
    public List<ProductDTO> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                return List.of(); // Return empty list instead of null
            }
            return products.stream()
                    .map(product -> ProductDTO.builder()
                                .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .createdAt(product.getCreatedAt())
                            .build())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve products: " + e.getMessage());
        }
    }

    @Override
    @CachePut(value = { "products", "productsAll" }, cacheManager = "cacheManager")
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setId(id);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(product);

        ProductDTO updatedDTO = new ProductDTO();
        updatedDTO.setId(updatedProduct.getId());
        updatedDTO.setName(updatedProduct.getName());
        updatedDTO.setDescription(updatedProduct.getDescription());
        updatedDTO.setPrice(updatedProduct.getPrice());

        return updatedDTO;
    }

    @Override
    @CacheEvict(value = { "products", "productsAll" }, cacheManager = "cacheManager")
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
