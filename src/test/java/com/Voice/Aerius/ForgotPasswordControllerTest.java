package com.Voice.Aerius;

import com.Voice.Aerius.Auth.controller.ForgotPasswordController;
import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.UserRepository;
import com.Voice.Aerius.Auth.service.EmailService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ForgotPasswordControllerTest {

    private EmailService emailService;
    private UserRepository userRepository;
    private ForgotPasswordController controller;

    @BeforeEach
    public void setUp() {
        emailService = mock(EmailService.class);
        userRepository = mock(UserRepository.class);
        controller = new ForgotPasswordController(emailService, userRepository);
    }

    @Test
    public void testSendForgotPasswordMail_success() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        controller.buckets.put(email, Bucket4j.builder()
                .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
                .build());

        ResponseEntity<String> response = controller.sendForgotPasswordMail(email);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Password reset mail sent to your mail", response.getBody());

        verify(emailService, times(1)).sendForgotPasswordMail(email);
    }

    @Test
    public void testSendForgotPasswordMail_userNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        controller.buckets.put(email, Bucket4j.builder()
                .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
                .build());

        ResponseEntity<String> response = controller.sendForgotPasswordMail(email);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Password reset mail sent to your mail", response.getBody());

        verify(emailService, times(0)).sendForgotPasswordMail(email);
    }

    @Test
    public void testSendForgotPasswordMail_tooManyRequests() {
        String email = "flood@example.com";
        Bucket limitedBucket = Bucket4j.builder()
                .addLimit(Bandwidth.classic(1, Refill.greedy(1, Duration.ofMinutes(1))))
                .build();
        limitedBucket.tryConsume(1); // consume the only token

        controller.buckets.put(email, limitedBucket);

        ResponseEntity<String> response = controller.sendForgotPasswordMail(email);
        assertEquals(HttpStatusCode.valueOf(429), response.getStatusCode());
        assertEquals("Too many requests, try after 60 sec", response.getBody());

        verify(emailService, times(0)).sendForgotPasswordMail(email);
    }

    @Test
    public void testSendForgotPasswordMail_exceptionHandling() {
        String email = "exception@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        doThrow(new RuntimeException("Failed")).when(emailService).sendForgotPasswordMail(email);

        controller.buckets.put(email, Bucket4j.builder()
                .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
                .build());

        ResponseEntity<String> response = controller.sendForgotPasswordMail(email);
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertEquals("Something went wrong", response.getBody());
    }
}

