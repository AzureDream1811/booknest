package com.azure.booknest.book.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookByExternalIdRequest(
    @NotNull(message = "External ID cannot be null")
    String externalId
) {
    
}
