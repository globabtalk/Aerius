package com.Voice.Aerius.dto.request;

import com.Voice.Aerius.Auth.enums.Role;
import jakarta.validation.Valid;
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
    @Size(min =8 ,message = "must be atleast 8 characters")
    private String password;


    @NotNull(message="email cannot be blank")
    @Email(message = "enter a valid email")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must be a valid email address format"
    )
    private String email;
}
