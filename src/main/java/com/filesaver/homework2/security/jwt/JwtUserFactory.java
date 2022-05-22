package com.filesaver.homework2.security.jwt;

import com.filesaver.homework2.domain.User;

public final class JwtUserFactory {
    public JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
