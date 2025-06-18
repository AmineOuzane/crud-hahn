package org.sid.crudcandidaturehahn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    private String id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "first_name", nullable = false)
    private String password;
    @Column(name = "last_name", nullable = false)
    private String confirmPassword;
    @Column(name = "phone", nullable = false)
    private String phone;
    @OneToMany(mappedBy = "appUser")
    private List<Product> products; // Assuming Product is another entity in your application
}
