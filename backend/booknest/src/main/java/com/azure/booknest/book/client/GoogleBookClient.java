package com.azure.booknest.book.client;

import com.azure.booknest.book.dto.response.GoogleBooksResponse;

public interface GoogleBookClient {
    
    public GoogleBooksResponse searchBooks(String query);
}
