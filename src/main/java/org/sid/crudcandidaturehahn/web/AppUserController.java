package org.sid.crudcandidaturehahn.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.dto.ResponseUserDTO;
import org.sid.crudcandidaturehahn.dto.UserDTO;
import org.sid.crudcandidaturehahn.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user accounts.
 * Provides endpoints to register a new user and delete an existing user account.
 * Handles requests related to user management.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseUserDTO> registerUser(@RequestBody UserDTO userDTO) {
        try {
            log.info("Starting user registration process");
            if (userDTO == null) {
                log.error("User registration failed: request body is null");
                return ResponseEntity.badRequest().build();
            }
            log.debug("Processing registration for user: {}", userDTO);
            ResponseUserDTO response = appUserService.registerUser(userDTO);
            log.info("User registration successful");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Invalid user data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("User registration failed: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        try {
            log.info("Attempting to delete user with ID: {}", userId);
            appUserService.deleteMyAccount(userId);
            log.info("User with ID {} successfully deleted", userId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting user with ID {}: {}", userId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
