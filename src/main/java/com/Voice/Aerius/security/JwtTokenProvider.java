package com.Voice.Aerius.security;

import com.Voice.Aerius.Auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Configuration
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationMs;

    @Value("${app.jwt-refresh-token-expiration-milliseconds}")
    private Long jwtRefreshTokenExpirationMs;

    private SecretKey keySecret(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateAccessToken(User user){
        Instant timeGen =Instant.now();
        Instant expiryDate=timeGen.plusMillis(jwtExpirationMs);

        Claims claim = Jwts.claims()
                .subject(user.getEmail())
                .add("roles", user.getRole().toString())
                .add("userId", user.getId())
                .build();

        return Jwts.builder()
                .claims(claim)
                .issuedAt(Date.from(timeGen))
                .expiration(Date.from(expiryDate))
                .signWith(keySecret())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(keySecret()).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(keySecret())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}