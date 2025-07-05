package com.Voice.Aerius.Auth.dto;

import com.Voice.Aerius.Auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private  boolean isVerified;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;






}
