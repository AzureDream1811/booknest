package com.azure.booknest.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Display name cannot be blank")
    String displayName,
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    String email,
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password
) {
    
}
