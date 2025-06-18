package org.sid.crudcandidaturehahn.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.dto.ProductDTO;
import org.sid.crudcandidaturehahn.dto.ResponseProductDTO;
import org.sid.crudcandidaturehahn.entities.Product;
import org.sid.crudcandidaturehahn.repositories.ProductRepository;
import org.sid.crudcandidaturehahn.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing products.
 * Provides methods to create, retrieve, update, and delete products.
 */
@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseProductDTO createProduct(ProductDTO productDTO) {
        try {
            // Validate input
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
                    .name(productDTO.getName().trim())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .createdAt(productDTO.getCreatedAt())
                    .build();
            productRepository.save(product);

            return ResponseProductDTO.builder()
                    .name(product.getName())
                    .createdAt(product.getCreatedAt())
                    .build();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    @Override
    public ProductDTO getProductById(Long id) {

        return productRepository.findById(id)
                .map(product -> ProductDTO.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .createdAt(product.getCreatedAt())
                        .build())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductDTO.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .createdAt(product.getCreatedAt())
                        .build())
                .toList();    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCreatedAt(productDTO.getCreatedAt());

        productRepository.save(product);

        return productDTO;
}

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
