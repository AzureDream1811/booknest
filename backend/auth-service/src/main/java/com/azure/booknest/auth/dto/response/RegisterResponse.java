package com.azure.booknest.auth.dto.response;

public record RegisterResponse(
    Long id,
    String displayName,
    String email
) {
    
}
