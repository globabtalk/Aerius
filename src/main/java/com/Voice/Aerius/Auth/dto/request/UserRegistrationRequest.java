package com.Voice.Aerius.Auth.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequest {
    @NotNull(message="first name cannot be blank")
    @Size(min = 3, max = 19,message = "must be between 3 and 19")
    private String firstName;

    @NotNull(message="first name cannot be blank")
    @Size(min = 3, max = 19,message = "must be between 3 and 19")
    private String lastName;

    @NotNull(message="password cannot be blank")
    @Size(min = 8, message = "must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;


    @NotNull(message="email cannot be blank")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must be a valid email address format"
    )
    @Email(message = "enter a valid email")
    private String email;
}
