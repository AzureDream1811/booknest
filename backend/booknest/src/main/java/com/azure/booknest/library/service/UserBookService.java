package com.azure.booknest.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azure.booknest.auth.entity.User;
import com.azure.booknest.auth.repository.UserRepository;
import com.azure.booknest.book.entity.Book;
import com.azure.booknest.book.repository.BookRepository;
import com.azure.booknest.common.exception.BadRequestException;
import com.azure.booknest.common.exception.ConflictException;
import com.azure.booknest.common.exception.ResourceNotFoundException;
import com.azure.booknest.library.dto.response.UserBookResponse;
import com.azure.booknest.library.dto.response.UserLibraryResponse;
import com.azure.booknest.library.entity.ReadingStatus;
import com.azure.booknest.library.entity.UserBook;
import com.azure.booknest.library.repository.UserBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public UserBookResponse addBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (userBookRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new ConflictException("Book already exists in user's library");
        }

        UserBook userBook = UserBook.builder()
                .user(user)
                .book(book)
                .status(ReadingStatus.WANT_TO_READ)
                .build();

        UserBook saved = userBookRepository.save(userBook);

        return new UserBookResponse(
                saved.getId(),
                saved.getUser().getId(),
                saved.getBook().getId(),
                saved.getStatus().name());
    }

    @Transactional(readOnly = true)
    public UserLibraryResponse getUserLibrary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<UserBookResponse> userBooks = userBookRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new UserLibraryResponse(userBooks);
    }

    private UserBookResponse mapToResponse(UserBook userBook) {
        return new UserBookResponse(
                userBook.getId(),
                userBook.getUser().getId(),
                userBook.getBook().getId(),
                userBook.getStatus().name());
    }

    @Transactional(readOnly = true)
    public UserLibraryResponse getUserLibraryByStatus(Long userId, ReadingStatus status) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        List<UserBookResponse> userBooks = userBookRepository
                .findAllByUserIdAndStatusOrderByCreatedAtDesc(userId, status)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new UserLibraryResponse(userBooks);
    }

    @Transactional
    public UserBookResponse updateReadingProgress(Long userId, Long bookId, Integer currentPage) {
        UserBook userBook = getUserBook(userId, bookId);

        if (currentPage == null || currentPage < 0) {
            throw new BadRequestException("Current page must be non-negative");
        }

        if (userBook.getBook().getPageCount() != null
                && currentPage > userBook.getBook().getPageCount()) {
            throw new BadRequestException("Current page exceeds book page count");
        }

        userBook.setCurrentPage(currentPage);

        return mapToResponse(userBook);
    }

    @Transactional
    public UserBookResponse updateReadingStatus(Long userId, Long bookId, ReadingStatus status) {
        UserBook userBook = getUserBook(userId, bookId);

        if (status == null) {
            throw new BadRequestException("Reading status is required");
        }

        userBook.setStatus(status);

        if (status == ReadingStatus.READING && userBook.getStartedAt() == null) {
            userBook.setStartedAt(LocalDate.now());
        }

        if (status == ReadingStatus.COMPLETED) {
            userBook.setFinishedAt(LocalDate.now());

            if (userBook.getBook().getPageCount() != null) {
                userBook.setCurrentPage(userBook.getBook().getPageCount());
            }
        } else {
            userBook.setFinishedAt(null);
        }

        return mapToResponse(userBook);
    }

    @Transactional
    public void removeBook(Long userId, Long bookId) {
        UserBook userBook = getUserBook(userId, bookId);
        userBookRepository.delete(userBook);
    }

    private UserBook getUserBook(Long userId, Long bookId) {
    return userBookRepository.findByUserIdAndBookId(userId, bookId)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Book not found in user's library"));
}
}
