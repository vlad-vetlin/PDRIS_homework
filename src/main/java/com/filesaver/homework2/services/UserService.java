package com.filesaver.homework2.services;

import com.filesaver.homework2.domain.User;
import com.filesaver.homework2.repositories.db.UserRepository;
import com.filesaver.homework2.requests.user.CreateRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User create(CreateRequest request) {
        User user = new User(
            request.getName(),
            request.getEmail(),
            request.getPhone(),
            encoder.encode(request.getPassword())
        );

        return repository.create(user);
    }
}
