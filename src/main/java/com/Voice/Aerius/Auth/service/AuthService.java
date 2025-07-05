package com.Voice.Aerius.Auth.service;

import com.Voice.Aerius.Auth.interfaces.SignAuthService;
import com.Voice.Aerius.Auth.dto.UserData;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{
    private final SignAuthService signAuthService;

    public  UserResponse<UserData> signup(UserRegistrationRequest regrequest){
        return signAuthService.signup(regrequest);
   }
}
