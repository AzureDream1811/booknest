package com.azure.booknest.auth.dto.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {
    
}
