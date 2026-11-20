
package com.azure.booknest.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.booknest.library.dto.request.ReadingProgressRequest;
import com.azure.booknest.library.dto.request.ReadingStatusRequest;
import com.azure.booknest.library.dto.response.UserBookResponse;
import com.azure.booknest.library.dto.response.UserLibraryResponse;
import com.azure.booknest.library.entity.ReadingStatus;
import com.azure.booknest.library.service.UserBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class UserBookController {

    private final UserBookService userBookService;

    @PostMapping("/books/{bookId}")
    public ResponseEntity<UserBookResponse> addBook(
            @RequestParam("userId") Long userId,
            @PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(userBookService.addBook(userId, bookId));
    }

    @GetMapping
    public ResponseEntity<UserLibraryResponse> getLibrary(
            @RequestParam("userId") Long userId,
            @RequestParam(name = "status", required = false) ReadingStatus status) {

        UserLibraryResponse response = status == null
                ? userBookService.getUserLibrary(userId)
                : userBookService.getUserLibraryByStatus(userId, status);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/books/{bookId}/progress")
    public ResponseEntity<UserBookResponse> updateProgress(
            @RequestParam("userId") Long userId,
            @PathVariable("bookId") Long bookId,
            @Valid @RequestBody ReadingProgressRequest request) {

        return ResponseEntity.ok(
                userBookService.updateReadingProgress(
                        userId, bookId, request.currentPage()));
    }

    @PatchMapping("/books/{bookId}/status")
    public ResponseEntity<UserBookResponse> updateStatus(
            @RequestParam("userId") Long userId,
            @PathVariable("bookId") Long bookId,
            @Valid @RequestBody ReadingStatusRequest request) {

        return ResponseEntity.ok(
                userBookService.updateReadingStatus(
                        userId, bookId, request.status()));
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> removeBook(
            @RequestParam("userId") Long userId,
            @PathVariable("bookId") Long bookId) {

        userBookService.removeBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }
}
