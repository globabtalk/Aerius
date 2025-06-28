package com.Voice.Aerius;

import com.Voice.Aerius.Auth.service.CustomUserDetailsService;
import com.Voice.Aerius.dto.UserRegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;

    public AuthController(CustomUserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with an email and a password. The password is securely hashed before storing."
    )
    public ResponseEntity<?>register(@Valid @RequestBody UserRegistrationRequest request){
        try{
            userDetailsService.registerUser(request);
            return ResponseEntity.status(201).body("{\"message\": \"User registered successfully.\"}");
        }catch(RuntimeException ex){
            return ResponseEntity.badRequest().body("{\"message\": \"" + ex.getMessage() + "\"}");
        }
        }
    }


