package com.azure.booknest.book.dto.response;

public record GoogleBookItem(
    String id,
    GoogleBookVolumeInfo volumeInfo
) {
    
}
