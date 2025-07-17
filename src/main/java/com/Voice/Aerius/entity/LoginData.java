package com.Voice.Aerius.entity;

import com.Voice.Aerius.Auth.enums.Role;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record LoginData (
        String id,
        String firstName,
        String lastName,
        String email,
        Role role,
        boolean isVerified,
        String accessToken,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
){
}
