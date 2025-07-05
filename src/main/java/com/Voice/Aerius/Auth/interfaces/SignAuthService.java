package com.Voice.Aerius.Auth.interfaces;

import com.Voice.Aerius.dto.UserData;
import com.Voice.Aerius.dto.request.UserRegistrationRequest;
import com.Voice.Aerius.dto.response.UserResponse;

public interface SignAuthService {
    UserResponse<UserData> signup(UserRegistrationRequest regrequest);

}
