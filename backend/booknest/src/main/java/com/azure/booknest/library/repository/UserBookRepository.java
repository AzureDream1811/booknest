package com.azure.booknest.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azure.booknest.library.entity.ReadingStatus;
import com.azure.booknest.library.entity.UserBook;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    Optional<UserBook> findByUserIdAndBookId(Long userId, Long bookId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    List<UserBook> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<UserBook> findAllByUserIdAndStatusOrderByCreatedAtDesc(Long userId, ReadingStatus status);
}
