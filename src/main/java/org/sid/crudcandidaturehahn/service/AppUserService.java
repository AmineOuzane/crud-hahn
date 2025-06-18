package org.sid.crudcandidaturehahn.service;

import org.sid.crudcandidaturehahn.dto.ResponseUserDTO;
import org.sid.crudcandidaturehahn.dto.UserDTO;
import org.sid.crudcandidaturehahn.entities.AppUser;

/**
 * AppUserService interface for managing user accounts.
 * Provides methods to register a new user and delete an existing user account.
 */
public interface AppUserService {

    ResponseUserDTO registerUser(UserDTO registrationUserDTO);
     void deleteMyAccount(String username);


    AppUser loadUserByUsername(String username);
}
