package com.azure.booknest.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azure.booknest.auth.dto.request.LoginRequest;
import com.azure.booknest.auth.dto.request.RegisterRequest;
import com.azure.booknest.auth.dto.response.LoginResponse;
import com.azure.booknest.auth.dto.response.RegisterResponse;
import com.azure.booknest.auth.entity.RefreshToken;
import com.azure.booknest.auth.entity.User;
import com.azure.booknest.auth.repository.RefreshTokenRepository;
import com.azure.booknest.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);

        return new RegisterResponse(savedUser.getId(), savedUser.getDisplayName(), savedUser.getEmail());
    }

    private User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = authenticate(request.email(), request.password());

        String accessToken = jwtService.generateAccessToken(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        String refreshTokenValue = jwtService.generateRefreshToken(user);
        refreshToken.setTokenHash(hashToken(refreshTokenValue));
        refreshToken.setExpiresAt(
                LocalDateTime.now().plusSeconds(jwtService.getRefreshTokenLifetimeSeconds()));

        refreshTokenRepository.save(refreshToken);

        return new LoginResponse(accessToken, refreshTokenValue);
    }

    private String hashToken(String token) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }

}