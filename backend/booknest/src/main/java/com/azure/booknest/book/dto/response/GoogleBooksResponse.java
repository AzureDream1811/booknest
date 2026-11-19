package com.azure.booknest.book.dto.response;

import java.util.List;

public record GoogleBooksResponse(
    Integer totalItems,
    List<GoogleBookItem> items
) {
    
}
