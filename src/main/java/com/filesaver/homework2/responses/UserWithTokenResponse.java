package com.filesaver.homework2.responses;

import com.filesaver.homework2.domain.User;
import lombok.Data;

@Data
public class UserWithTokenResponse {
    private UserResponse user;

    private String token;

    public UserWithTokenResponse(User user, String token) {
        this.user = new UserResponse(user);
        this.token = token;
    }
}
