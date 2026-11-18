package com.quickbite.user.service;

import com.quickbite.user.dto.AuthResponse;
import com.quickbite.user.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
}
