package com.filesaver.homework2.controllers;

import com.filesaver.homework2.domain.User;
import com.filesaver.homework2.repositories.db.UserRepository;
import com.filesaver.homework2.requests.user.LoginRequest;
import com.filesaver.homework2.responses.UserWithTokenResponse;
import com.filesaver.homework2.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository repository;

    public LoginController(
            AuthenticationManager authenticationManager,
            UserRepository repository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public UserWithTokenResponse login(@Valid @RequestBody LoginRequest loginUserRequest) {
        try {
            String email = loginUserRequest.getEmail();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, loginUserRequest.getPassword(), new ArrayList<>())
            );

            User user = repository.findByEmail(email).stream().findFirst().orElseThrow();

            String token = jwtTokenProvider.createToken(email);

            return new UserWithTokenResponse(user, token);
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Not valid credentials");
        }
    }
}
