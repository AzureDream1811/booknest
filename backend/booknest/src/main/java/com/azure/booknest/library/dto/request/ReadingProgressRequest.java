package com.azure.booknest.library.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ReadingProgressRequest(
    @NotNull
    @PositiveOrZero
    Integer currentPage
) {
    
}
