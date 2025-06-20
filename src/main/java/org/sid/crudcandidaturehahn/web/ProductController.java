package org.sid.crudcandidaturehahn.web;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.dto.ProductDTO;
import org.sid.crudcandidaturehahn.dto.ResponseProductDTO;
import org.sid.crudcandidaturehahn.entities.Product;
import org.sid.crudcandidaturehahn.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing products.
 * Provides endpoints to create, retrieve, update, and delete products.
 * Handles requests related to product management.
 */
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    @PostMapping("/create-product")
    public ResponseEntity<ResponseProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ResponseProductDTO product = productService.createProduct(productDTO);
            log.info("Product created successfully: {}", product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

        @GetMapping("/list-all-products")
        public ResponseEntity<List<ProductDTO>> getAllProducts() {
            try {
                List<ProductDTO> products = productService.getAllProducts();
                if (products.isEmpty()) {
                    log.info("No products found.");
                    return ResponseEntity.noContent().build();
                }
                log.info("Retrieved {} products.", products.size());
                return ResponseEntity.ok(products);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @GetMapping("/get-product/{id}")
        public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
            try {
                ProductDTO product = productService.getProductById(id);
                if (product == null) {
                    log.warn("Product with ID {} not found.", id);
                    return ResponseEntity.notFound().build();
                }
                log.info("Product with ID {} retrieved successfully: {}", id, product);
                return ResponseEntity.ok(product);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @PutMapping("/update-product/{id}")
        public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
            try {
                ProductDTO product = productService.updateProduct(id, productDTO);
                if (product == null) {
                    log.warn("Product with ID {} not found.", id);
                    return ResponseEntity.notFound().build();
                }
                log.info("Product with ID {} updated successfully: {}", id, product);
                return ResponseEntity.ok(product);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @DeleteMapping("/delete-product/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
            try {
                productService.deleteProduct(id);
                log.info("Product with ID {} deleted successfully.", id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
}
