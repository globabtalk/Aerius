package com.Voice.Aerius.Auth.service;

import com.Voice.Aerius.Auth.dto.request.LoginRequestDto;
import com.Voice.Aerius.Auth.interfaces.LoginService;
import com.Voice.Aerius.Auth.interfaces.SignAuthService;
import com.Voice.Aerius.entity.LoginData;
import com.Voice.Aerius.entity.UserData;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{
    private final SignAuthService signAuthService;
    private final LoginService loginService;


    public  UserResponse<UserData> signup(UserRegistrationRequest regrequest){
        return signAuthService.signup(regrequest);
   }

    public UserResponse<LoginData> login(LoginRequestDto request) {
        return loginService.login(request);
    }
}
