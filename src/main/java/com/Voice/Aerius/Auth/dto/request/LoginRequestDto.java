package com.Voice.Aerius.Auth.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginRequestDto(
        @NotNull(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Password is required")
        @Schema(description = "Password of the registered user", example = "P@ssw0rd123")
        String password
) {
}
