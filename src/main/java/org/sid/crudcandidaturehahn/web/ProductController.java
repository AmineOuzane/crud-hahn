package org.sid.crudcandidaturehahn.web;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.dto.ProductDTO;
import org.sid.crudcandidaturehahn.dto.ResponseProductDTO;
import org.sid.crudcandidaturehahn.entities.Product;
import org.sid.crudcandidaturehahn.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<ResponseProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ResponseProductDTO product = productService.createProduct(productDTO);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

        @GetMapping("/list-all-products")
        public ResponseEntity<List<ProductDTO>> getAllProducts() {
            try {
                List<ProductDTO> products = productService.getAllProducts();
                return ResponseEntity.ok(products);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @PutMapping("/get-product/{id}")
        public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
            try {
                ProductDTO product = productService.updateProduct(id, productDTO);
                return ResponseEntity.ok(product);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @DeleteMapping("/delete-product/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
            try {
                productService.deleteProduct(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
}
