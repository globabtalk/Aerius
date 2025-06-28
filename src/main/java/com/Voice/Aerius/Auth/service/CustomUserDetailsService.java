package com.Voice.Aerius.Auth.service;
import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.UserRepository;
import com.Voice.Aerius.dto.UserRegistrationRequest;
import com.Voice.Aerius.dto.UserResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    public User loadUserbyId(Long userId) {
        return userRepository.findByEmail(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    ;

    public CustomUserDetailsService(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponce registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("user");

        User savedUser = userRepository.save(newUser);
        return new UserResponce(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }


}
