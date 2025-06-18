package org.sid.crudcandidaturehahn.repositories;

import org.sid.crudcandidaturehahn.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
