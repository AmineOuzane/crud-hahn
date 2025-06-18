package org.sid.crudcandidaturehahn.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.entities.AppUser;
import org.sid.crudcandidaturehahn.service.AppUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserService.loadUserByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

        // User type UserDetails
        return User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .build();
    }
}
