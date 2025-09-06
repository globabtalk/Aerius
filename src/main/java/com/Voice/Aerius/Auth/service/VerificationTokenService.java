package com.Voice.Aerius.Auth.service;

import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.VerificationTokenRepository;
import com.Voice.Aerius.entity.VerificationToken;
import jakarta.transaction.Transactional;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;


@Service
    public class VerificationTokenService {

        @Autowired
        private VerificationTokenRepository tokenRepository;

        @Value("${app.verification.token.expiry-hours:24}")
        private int tokenExpiryHours;

        private final SecureRandom secureRandom = new SecureRandom();

        @Transactional
        public VerificationToken createVerificationToken(User user) {
            // Delete existing tokens for this user
            tokenRepository.deleteByUserId(user.getId());

            String token = generateSecureToken();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(tokenExpiryHours);

            VerificationToken verificationToken = new VerificationToken(token, user, expiryDate);
            return tokenRepository.save(verificationToken);
        }

        public Optional<VerificationToken> findByToken(String token) {
            return tokenRepository.findByToken(token);
        }

        @Transactional
        public boolean verifyToken(String token) {
            Optional<VerificationToken> tokenOpt = tokenRepository.findByToken(token);

            if (tokenOpt.isEmpty()) {
                return false;
            }

            VerificationToken verificationToken = tokenOpt.get();

            if (verificationToken.isUsed() || verificationToken.isExpired()) {
                return false;
            }

            verificationToken.setUsed(true);
            tokenRepository.save(verificationToken);

            return true;
        }

        private String generateSecureToken() {
            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        }

        @Transactional
        public void cleanupExpiredTokens() {
            tokenRepository.deleteExpiredTokens(LocalDateTime.now());
        }
    }
}
