package com.filesaver.homework2.security;

import com.filesaver.homework2.repositories.db.UserRepository;
import com.filesaver.homework2.security.jwt.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public JwtUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return JwtUserFactory.create(repository.findByEmail(username).stream().findFirst().orElseThrow());
    }
}
