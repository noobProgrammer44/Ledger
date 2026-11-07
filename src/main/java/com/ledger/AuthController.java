package com.ledger;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserStore userStore, PasswordEncoder passwordEncoder) {
        this.userStore = userStore;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public UserResponse signup(@Valid @RequestBody SignupRequest request) {
        if (userStore.findByEmail(request.email()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        String hash = passwordEncoder.encode(request.password());
        User saved = userStore.save(request.email(), hash);
        return new UserResponse(saved.id(), saved.email());
    }
}