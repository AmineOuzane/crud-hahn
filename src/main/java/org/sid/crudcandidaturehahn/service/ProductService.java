package org.sid.crudcandidaturehahn.service;

import org.sid.crudcandidaturehahn.dto.ProductDTO;

import java.util.List;

/**
 * ProductService interface for managing products.
 * Provides methods to create, retrieve, update, and delete products.
 */

public interface ProductService {

     ProductDTO createProduct(ProductDTO productDTO);
     ProductDTO getProductById(String id);
     List<ProductDTO> getAllProducts();
     ProductDTO updateProduct(String id, ProductDTO productDTO);
     void deleteProduct(String id);
}
