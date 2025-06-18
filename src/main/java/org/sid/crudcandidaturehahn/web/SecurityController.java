package org.sid.crudcandidaturehahn.web;

import lombok.AllArgsConstructor;
import org.sid.crudcandidaturehahn.entities.AppUser;
import org.sid.crudcandidaturehahn.repositories.AppUserRepository;
import org.sid.crudcandidaturehahn.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);


    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        log.info("Authenticating user: {}", username);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        log.info("User authenticated: {}", authentication.getName());

        // Get the username from the authenticated principal (standard User object)
        String authenticatedUsername = authentication.getName();

        // Load the AppUser object using the username
        AppUser appUser = appUserService.loadUserByUsername(authenticatedUsername);

        if (appUser == null) {
            throw new IllegalStateException("Authenticated user not found in service after authentication.");
        }
        String userId = appUser.getId();


        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet= JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("userId", userId)
                .build();

        // Create JWT header
        JwtEncoderParameters jwtEncoderParamters =
                JwtEncoderParameters.from(
                        // Use same ALGO HS512 in the jwtDecoder
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        // Encode the JWT token
        String jwt = jwtEncoder.encode(jwtEncoderParamters).getTokenValue();

        return Map.of("access_token", jwt);
    }

}
