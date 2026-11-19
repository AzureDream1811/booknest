package com.azure.booknest.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azure.booknest.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);

    List<Book> findByIsbn10(String isbn10);

    List<Book> findByIsbn13(String isbn13);

}
