package com.Voice.Aerius.Auth.interfaces;

import com.Voice.Aerius.Auth.dto.request.LoginRequestDto;
import com.Voice.Aerius.Auth.dto.response.UserResponse;
import com.Voice.Aerius.entity.LoginData;

public interface LoginService {
    UserResponse<LoginData> login(LoginRequestDto request);
}

