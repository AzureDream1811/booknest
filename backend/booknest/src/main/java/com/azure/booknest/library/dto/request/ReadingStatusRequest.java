package com.azure.booknest.library.dto.request;

import com.azure.booknest.library.entity.ReadingStatus;

import jakarta.validation.constraints.NotNull;

public record ReadingStatusRequest(
        @NotNull ReadingStatus status) {

}
