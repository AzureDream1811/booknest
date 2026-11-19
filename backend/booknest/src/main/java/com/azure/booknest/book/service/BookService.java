package com.azure.booknest.book.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azure.booknest.book.entity.Book;
import com.azure.booknest.book.repository.BookRepository;
import com.azure.booknest.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

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
}
