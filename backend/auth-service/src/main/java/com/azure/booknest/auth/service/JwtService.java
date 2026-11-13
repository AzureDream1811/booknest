package com.azure.booknest.auth.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.azure.booknest.auth.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    private static final long ACCESS_EXPIRATION_TIME = 900; // 15 minutes
    static final long REFRESH_EXPIRATION_TIME = 604800; // 7 days

    public long getRefreshTokenLifetimeSeconds() {
        return REFRESH_EXPIRATION_TIME;
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("booknest")
                .audience(List.of("booknest-api"))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_EXPIRATION_TIME))
                .subject(user.getId().toString())
                .id(UUID.randomUUID().toString())
                .claim("token_type", "access")
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();

    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("booknest")
                .audience(List.of("booknest-api"))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(REFRESH_EXPIRATION_TIME))
                .subject(user.getId().toString())
                .id(UUID.randomUUID().toString())
                .claim("token_type", "refresh")
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
