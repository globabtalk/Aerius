package com.Voice.Aerius.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voice.Aerius.Auth.model.User;
import com.Voice.Aerius.Repository.UserRepository;

@Service
public class PasswordResetTokenService {

    private UserRepository userRepository;
    
    @Autowired
    public PasswordResetTokenService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String createOrRetrieveToken(String email){
        User user = userRepository.findByEmail(email);
        if(user.getToken() != null && user.getTokenExpiry().plusHours(1).isBefore(LocalDateTime.now()))
            return user.getToken();
        else
        {
            String newToken = UUID.randomUUID().toString();
            user.setToken(newToken);
            user.setTokenExpiry(LocalDateTime.now().plusHours(1));
            userRepository.save(user);
            return newToken;
        }
    }
}
