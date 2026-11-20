package com.azure.booknest.library.dto.response;

import java.util.List;

public record UserLibraryResponse(
    List<UserBookResponse> books
) {
    
}
