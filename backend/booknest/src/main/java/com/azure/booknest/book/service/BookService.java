package com.azure.booknest.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azure.booknest.book.client.GoogleBookClient;
import com.azure.booknest.book.dto.response.GoogleBookIndustryIdentifier;
import com.azure.booknest.book.dto.response.GoogleBookItem;
import com.azure.booknest.book.dto.response.GoogleBookVolumeInfo;
import com.azure.booknest.book.dto.response.GoogleBooksResponse;
import com.azure.booknest.book.entity.Book;
import com.azure.booknest.book.repository.BookRepository;
import com.azure.booknest.common.exception.ExternalApiException;
import com.azure.booknest.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final GoogleBookClient googleBookClient;

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        return book;
    }

    @Transactional(readOnly = true)
    public Book getBookByExternalId(String externalId) {
        Book book = bookRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with External ID: " + externalId));

        return book;
    }

    public GoogleBooksResponse searchBooks(String query) {
        return googleBookClient.searchBooks(query);
    }

    @Transactional
    public Book importBook(String externalId) {
        return bookRepository.findByExternalId(externalId)
                .orElseGet(() -> {
                    GoogleBookItem googleBookItem = fetchGoogleBookByExternalId(externalId);
                    Book book = mapToBook(googleBookItem);
                    return bookRepository.save(book);
                });
    }

    private GoogleBookItem fetchGoogleBookByExternalId(String externalId) {
        if (externalId == null || externalId.isBlank()) {
            throw new ExternalApiException("External ID is required");
        }

        GoogleBookItem googleBookItem = googleBookClient.getBookByExternalId(externalId);

        if (googleBookItem == null) {
            throw new ExternalApiException("Book not found for External ID: " + externalId);
        }

        if (googleBookItem.id() == null || googleBookItem.id().isBlank()) {
            throw new ExternalApiException("Returned External ID is missing");
        }

        if (!externalId.equals(googleBookItem.id())) {
            throw new ExternalApiException(
                    "Returned External ID does not match requested External ID: " + externalId);
        }

        return googleBookItem;
    }

    private Book mapToBook(GoogleBookItem googleBookItem) {
        if (googleBookItem == null) {
            throw new ExternalApiException("Google Books item is required");
        }

        GoogleBookVolumeInfo info = googleBookItem.volumeInfo();

        if (info == null) {
            throw new ExternalApiException(
                    "Volume info not found for External ID: " + googleBookItem.id());
        }

        if (info.title() == null || info.title().isBlank()) {
            throw new ExternalApiException(
                    "Title is required for External ID: " + googleBookItem.id());
        }

        List<GoogleBookIndustryIdentifier> identifiers = info.industryIdentifiers() != null
                ? info.industryIdentifiers()
                : List.of();

        return Book.builder()
                .externalId(googleBookItem.id())
                .title(info.title())
                .subtitle(info.subtitle())
                .authors(info.authors())
                .description(info.description())
                .publisher(info.publisher())
                .publishedDate(info.publishedDate())
                .description(info.description())
                .isbn10(identifiers.stream()
                        .filter(identifier -> "ISBN_10".equals(identifier.type()))
                        .map(GoogleBookIndustryIdentifier::identifier)
                        .filter(identifier -> identifier != null && !identifier.isBlank())
                        .findFirst()
                        .orElse(null))
                .isbn13(identifiers.stream()
                        .filter(identifier -> "ISBN_13".equals(identifier.type()))
                        .map(GoogleBookIndustryIdentifier::identifier)
                        .filter(identifier -> identifier != null && !identifier.isBlank())
                        .findFirst()
                        .orElse(null))
                .pageCount(info.pageCount())
                .categories(info.categories())
                .thumbnailUrl(info.imageLinks() != null ? info.imageLinks().thumbnail() : null)
                .language(info.language())
                .build();
    }
}
