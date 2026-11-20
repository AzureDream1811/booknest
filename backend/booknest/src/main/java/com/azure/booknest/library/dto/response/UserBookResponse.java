package com.azure.booknest.library.dto.response;

public record UserBookResponse(
    Long id,
    Long userId,
    Long bookId,
    String status
) {
    
}
