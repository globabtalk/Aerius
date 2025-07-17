package com.Voice.Aerius.Auth.impl;

import com.Voice.Aerius.Auth.enums.Role;
import com.Voice.Aerius.Auth.interfaces.SignAuthService;
import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.UserRepository;
import com.Voice.Aerius.entity.UserData;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import com.Voice.Aerius.exceptions.customexceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class SignUpImpl implements SignAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse<UserData> signup(UserRegistrationRequest regrequest){
        if (userRepository.existsByEmail(regrequest.getEmail())) {
            throw new ConflictException("This email already exists");
        }

        String encoderpasswrd =passwordEncoder.encode(regrequest.getPassword());
        User user =new User();
        user.setFirstName(regrequest.getFirstName());
        user.setLastName(regrequest.getLastName());
        user.setEmail(regrequest.getEmail());
        user.setPassword(encoderpasswrd);
        user.setRole(Role.USER);
        user.setVerified(false);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        userRepository.save(user);

        UserData userData=new UserData();
        userData.setId(user.getId());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setEmail(user.getEmail());
        userData.setRole(user.getRole());
        userData.setVerified(user.isVerified());
        userData.setCreatedAt(user.getCreatedAt());
        userData.setUpdatedAt(user.getUpdatedAt());

        return UserResponse.<UserData>builder()
                .statusCode(HttpStatus.CREATED.value())
                .status("success")
                .message("user created successfully")
                .data(userData)
                .build();
    }

}
