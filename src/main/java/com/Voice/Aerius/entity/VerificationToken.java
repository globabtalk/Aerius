package com.Voice.Aerius.entity;

import com.Voice.Aerius.Auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
    @Table(name = "verification_tokens")
    public class VerificationToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String token;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(nullable = false)
        private LocalDateTime expiryDate;

        @Column(nullable = false)
        private boolean used = false;

    }
