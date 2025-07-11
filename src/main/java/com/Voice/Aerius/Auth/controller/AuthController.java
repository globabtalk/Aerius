package com.Voice.Aerius.Auth.controller;

import com.Voice.Aerius.Auth.dto.request.LoginRequest;
import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.UserRepository;
import com.Voice.Aerius.Auth.service.AuthService;
import com.Voice.Aerius.Auth.dto.UserData;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import com.Voice.Aerius.security.JwtResponse;
import com.Voice.Aerius.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with an email and a password. The password is securely hashed before storing."
    )
    public ResponseEntity<UserResponse<UserData>> register(@Valid @RequestBody UserRegistrationRequest request){
      return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
        }

    @PostMapping("/login")
    @Operation(
            summary = "Log in a registered user",
            description = "Authenticates the user and returns a JWT access token if credentials are valid."
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtTokenProvider.generateAccessToken(user);
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
    }


