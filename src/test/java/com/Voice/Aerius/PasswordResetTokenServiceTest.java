package com.Voice.Aerius;

import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.service.PasswordResetTokenService;
import com.Voice.Aerius.Auth.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PasswordResetTokenServiceTest {

    private UserRepository userRepository;
    private PasswordResetTokenService tokenService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        tokenService = new PasswordResetTokenService(userRepository);
    }

    @Test
    public void testRetrieveExistingValidToken() {
        String email = "test@example.com";
        String existingToken = UUID.randomUUID().toString();

        User user = new User();
        user.setEmail(email);
        user.setToken(existingToken);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(30)); // valid token

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        System.out.println(user.getToken() + " " + existingToken);
        String token = tokenService.createOrRetrieveToken(email);
        System.out.println(user.getToken() + " " + token);
        assertEquals(existingToken, token);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testCreateNewTokenWhenTokenIsExpired() {
        String email = "test@example.com";
        String oldToken = UUID.randomUUID().toString();

        User user = new User();
        user.setEmail(email);
        user.setToken(oldToken);
        user.setTokenExpiry(LocalDateTime.now().minusHours(1)); // expired

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String newToken = tokenService.createOrRetrieveToken(email);

        assertNotNull(newToken);
        assertNotEquals(oldToken, newToken);
        assertEquals(newToken, user.getToken());
        assertTrue(user.getTokenExpiry().isAfter(LocalDateTime.now()));

        verify(userRepository, times(1)).save(user);
    }
}

