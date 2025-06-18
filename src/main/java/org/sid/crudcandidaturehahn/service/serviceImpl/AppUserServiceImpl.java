package org.sid.crudcandidaturehahn.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.dto.ResponseUserDTO;
import org.sid.crudcandidaturehahn.dto.UserDTO;
import org.sid.crudcandidaturehahn.entities.AppUser;
import org.sid.crudcandidaturehahn.repositories.AppUserRepository;
import org.sid.crudcandidaturehahn.service.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for managing application users.
 * Provides methods for user registration and account deletion.
 */
@Service
@AllArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    @Override
    public ResponseUserDTO registerUser(UserDTO userDTO) {

        if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }

        if (userDTO.getPhone() == null || !userDTO.getPhone().matches("^\\+?[0-9]{7,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }

        // Check if username or email already exists (optional but recommended)
        if (appUserRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already taken.");
        }

        if (appUserRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        AppUser newUser = AppUser.builder()
                .id(UUID.randomUUID().toString())
                .username(userDTO.getUsername())
                .password(hashedPassword)
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .build();

        appUserRepository.save(newUser);

        return ResponseUserDTO.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();
     }


    @Override
    public void deleteMyAccount(String username) {
        appUserRepository.deleteByUsername(username);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

}
