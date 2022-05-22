package com.filesaver.homework2.domain;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
public class User {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String password;

    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public User() {}
}
