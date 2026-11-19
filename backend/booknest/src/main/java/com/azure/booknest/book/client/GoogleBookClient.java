package com.azure.booknest.book.client;

import com.azure.booknest.book.dto.response.GoogleBookItem;
import com.azure.booknest.book.dto.response.GoogleBooksResponse;

public interface GoogleBookClient {
    
    GoogleBooksResponse searchBooks(String query);

    GoogleBookItem getBookByExternalId(String externalId);
}
