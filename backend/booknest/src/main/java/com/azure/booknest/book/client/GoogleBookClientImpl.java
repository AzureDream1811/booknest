package com.azure.booknest.book.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.azure.booknest.book.dto.response.GoogleBooksResponse;
import com.azure.booknest.common.exception.ExternalApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleBookClientImpl implements GoogleBookClient {

    private final RestClient restClient;

    @Override
    // is4xxClientError() and is5xxServerError() are for specific error codes, so we
    // use isError() to handle all errors
    public GoogleBooksResponse searchBooks(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new ExternalApiException("Error occurred while calling Google Books API: " + response);
                })
                .body(GoogleBooksResponse.class);
    }

}
