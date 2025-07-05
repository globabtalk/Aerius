package com.Voice.Aerius.Auth.interfaces;

import com.Voice.Aerius.Auth.dto.UserData;
import com.Voice.Aerius.Auth.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.Auth.dto.response.UserResponse;

public interface SignAuthService {
    UserResponse<UserData> signup(UserRegistrationRequest regrequest);
}
