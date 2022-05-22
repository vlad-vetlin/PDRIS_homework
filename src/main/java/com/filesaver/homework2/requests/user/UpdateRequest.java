package com.filesaver.homework2.requests.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UpdateRequest {
    @Size(max=255)
    @NotEmpty
    @Email
    private String email;

    @Size(max=255)
    @NotEmpty
    private String name;

    @Size(max=255)
    @NotEmpty
    private String phone;
}
