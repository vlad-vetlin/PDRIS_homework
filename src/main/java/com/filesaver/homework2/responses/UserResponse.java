package com.filesaver.homework2.responses;

import com.filesaver.homework2.domain.User;
import lombok.Data;

@Data
public class UserResponse {
    private String name;

    private String email;

    private String phone;

    public UserResponse(User user) {
        name = user.getName();
        email = user.getEmail();
        phone = user.getPhone();
    }
}
