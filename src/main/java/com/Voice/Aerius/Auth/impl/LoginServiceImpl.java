package com.Voice.Aerius.Auth.impl;

import com.Voice.Aerius.Auth.dto.request.LoginRequestDto;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import com.Voice.Aerius.Auth.interfaces.LoginService;
import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.UserRepository;
import com.Voice.Aerius.entity.LoginData;
import com.Voice.Aerius.exceptions.customexceptions.ForbiddenException;
import com.Voice.Aerius.exceptions.customexceptions.NotFoundException;
import com.Voice.Aerius.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public UserResponse<LoginData> login(LoginRequestDto request) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isEmpty()) {
            throw new NotFoundException("User not found with email: " + request.email());
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (AuthenticationException e) {
            throw new ForbiddenException("Authentication failed. Please check your credentials.");
        }

        User authenticatedUser = (User) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateAccessToken(authenticatedUser);

        LoginData loginData = LoginData.builder()
                .id(authenticatedUser.getId())
                .firstName(authenticatedUser.getFirstName())
                .lastName(authenticatedUser.getLastName())
                .email(authenticatedUser.getEmail())
                .role(authenticatedUser.getRole())
                .accessToken(accessToken)
                .isVerified(authenticatedUser.isVerified())
                .createdAt(authenticatedUser.getCreatedAt())
                .updatedAt(authenticatedUser.getUpdatedAt())
                .build();

        return UserResponse.<LoginData>builder()
                .statusCode(HttpStatus.OK.value())
                .status("success")
                .message("User logged in successfully")
                .data(loginData)
                .build();
    }
}