package com.example.dto;

import com.example.annotations.annotation.Password;
import com.example.annotations.annotation.Username;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
public class RegistrationDTO {
    @Username
    private String username;

    @Email
    private String email;

    @Password
    private String password;
}
