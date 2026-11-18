package com.quickbite.user.service;

import com.quickbite.user.dto.AuthResponse;
import com.quickbite.user.dto.LoginRequest;
import com.quickbite.user.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String refreshToken);

    void logout(String refreshToken);
}
