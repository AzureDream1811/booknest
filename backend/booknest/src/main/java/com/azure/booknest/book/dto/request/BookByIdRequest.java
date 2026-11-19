package com.azure.booknest.book.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookByIdRequest(
    @NotNull(message = "Book ID cannot be null")
    Long bookId
) {
    
}
