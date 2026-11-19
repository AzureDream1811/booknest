package com.azure.booknest.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.booknest.book.dto.response.GoogleBooksResponse;
import com.azure.booknest.book.entity.Book;
import com.azure.booknest.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/external/{externalId}")
    public Book getBookByExternalId(@PathVariable String externalId) {
        return bookService.getBookByExternalId(externalId);
    }

    @GetMapping("/search")
    public ResponseEntity<GoogleBooksResponse> searchBooks(@RequestParam("q") String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }

}
