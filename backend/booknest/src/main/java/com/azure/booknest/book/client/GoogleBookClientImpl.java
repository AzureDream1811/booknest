package com.azure.booknest.book.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.azure.booknest.book.dto.response.GoogleBooksResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleBookClientImpl implements GoogleBookClient {

    private final RestClient restClient;

    @Override
    public GoogleBooksResponse searchBooks(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", query)
                        .build())
                .retrieve().body(GoogleBooksResponse.class);
    }

}
