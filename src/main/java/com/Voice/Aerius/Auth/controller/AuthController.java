package com.Voice.Aerius.Auth.controller;

import com.Voice.Aerius.entity.LoginData;
import com.Voice.Aerius.entity.UserData;
import com.Voice.Aerius.Auth.dto.request.LoginRequestDto;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import com.Voice.Aerius.Auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with an email and a password. The password is securely hashed before storing.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found - User with this email already exists"
                    )
            }
    )
    public ResponseEntity<UserResponse<UserData>> register(@Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "User Login",
            description = "Authenticates a user with email and password, returning user data if successful.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User logged in successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Forbidden - Invalid credentials or user not found"
                    )
            }
    )
    public ResponseEntity<UserResponse<LoginData>> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Login attempt for user: {}", request.email());
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }
}
