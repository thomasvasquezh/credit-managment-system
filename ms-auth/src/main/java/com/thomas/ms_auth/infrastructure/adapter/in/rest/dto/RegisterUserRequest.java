package com.thomas.ms_auth.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterUserRequest(
        @NotBlank(message = "firstName is required")
        @Size(min = 2, max = 80, message = "firstName must be between 2 and 80 characters")
        String firstName,

        @NotBlank(message = "lastName is required")
        @Size(min = 2, max = 80, message = "lastName must be between 2 and 80 characters")
        String lastName,

        @NotBlank(message = "email is required")
        @Email(message = "email must be a valid email address")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8, message = "password must have at least 8 characters")
        String password

) {
}
