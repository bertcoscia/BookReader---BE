package com.bertcoscia.BookReader.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Surname cannot be blank")
        String surname,

        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Insert a valid email")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character (@#$%^&+=!)")
        String password
) {
}
