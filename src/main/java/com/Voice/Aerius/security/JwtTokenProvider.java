package com.Voice.Aerius.security;

import com.Voice.Aerius.Auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationMs;

    @Value("${app.jwt-refresh-token-expiration-milliseconds}")
    private Long refreshTokenExpirationMs;

    private SecretKey keySecret(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

    }
    public String generateAccessToken(User user){
        Instant timeGen =Instant.now();
        Instant expiryDate=timeGen.plusMillis(jwtExpirationMs);
        Claims claim = Jwts.claims()
                .subject(user.getEmail())
                .add("roles",user.getRole())
                .add("userId",user.getId())
                .build();

        return Jwts.builder()
                .claims(claim)
                .issuedAt(Date.from(timeGen))
                .expiration(Date.from(expiryDate))
                .signWith(keySecret())
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(keySecret()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {

            return false;
        }

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


