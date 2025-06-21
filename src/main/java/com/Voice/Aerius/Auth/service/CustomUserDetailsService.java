package com.Voice.Aerius.Auth.service;
import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private  final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException
    {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Invalid email or password"));
    }

    public User loadUserbyId(String userId){
        return userRepository.findByEmail(userId)
                .orElseThrow(()->new RuntimeException("user not found"));
    };


}
