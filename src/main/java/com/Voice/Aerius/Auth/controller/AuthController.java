package com.Voice.Aerius.Auth.controller;

import com.Voice.Aerius.Auth.service.AuthService;
import com.Voice.Aerius.Auth.dto.UserData;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with an email and a password. The password is securely hashed before storing."
    )
    public ResponseEntity<UserResponse<UserData>> register(@Valid @RequestBody UserRegistrationRequest request){
      return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
        }
    }


