package com.filesaver.homework2.requests.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;

    private String password;
}
