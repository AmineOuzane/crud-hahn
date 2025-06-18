package org.sid.crudcandidaturehahn.repositories;

import org.sid.crudcandidaturehahn.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    // Additional query methods can be defined here if needed
}
