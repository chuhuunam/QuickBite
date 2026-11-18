package com.quickbite.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickbite.user.dto.AuthResponse;
import com.quickbite.user.dto.LoginRequest;
import com.quickbite.user.dto.LogoutRequest;
import com.quickbite.user.dto.RegisterRequest;
import com.quickbite.user.dto.UserResponse;
import com.quickbite.user.enums.UserRole;
import com.quickbite.user.exception.AccountLockedException;
import com.quickbite.user.exception.EmailAlreadyExistsException;
import com.quickbite.user.exception.InvalidCredentialsException;
import com.quickbite.user.service.AuthService;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("POST /api/auth/register - success returns 201")
    void register_Success() throws Exception {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .fullName("Test User")
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .user(UserResponse.builder()
                        .id(1L)
                        .email("test@example.com")
                        .fullName("Test User")
                        .role(UserRole.CUSTOMER)
                        .build())
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        // When/Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("access_token"))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.user.role").value("CUSTOMER"));
    }

    @Test
    @DisplayName("POST /api/auth/register - invalid email returns 400")
    void register_InvalidEmail_Returns400() throws Exception {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("invalid-email")
                .password("password123")
                .build();

        // When/Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("POST /api/auth/register - short password returns 400")
    void register_ShortPassword_Returns400() throws Exception {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("short")
                .build();

        // When/Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("POST /api/auth/register - duplicate email returns 409")
    void register_DuplicateEmail_Returns409() throws Exception {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("existing@example.com")
                .password("password123")
                .build();

        when(authService.register(any(RegisterRequest.class)))
                .thenThrow(new EmailAlreadyExistsException("existing@example.com"));

        // When/Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("EMAIL_EXISTS"));
    }

    @Test
    @DisplayName("POST /api/auth/login - success returns 200")
    void login_Success() throws Exception {
        // Given
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .user(UserResponse.builder()
                        .id(1L)
                        .email("test@example.com")
                        .fullName("Test User")
                        .role(UserRole.CUSTOMER)
                        .build())
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        // When/Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access_token"))
                .andExpect(jsonPath("$.user.email").value("test@example.com"));
    }

    @Test
    @DisplayName("POST /api/auth/login - invalid credentials returns 401")
    void login_InvalidCredentials_Returns401() throws Exception {
        // Given
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("wrongpassword")
                .build();

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new InvalidCredentialsException());

        // When/Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"));
    }

    @Test
    @DisplayName("POST /api/auth/login - account locked returns 423")
    void login_AccountLocked_Returns423() throws Exception {
        // Given
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new AccountLockedException(LocalDateTime.now().plusMinutes(15)));

        // When/Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isLocked())
                .andExpect(jsonPath("$.code").value("ACCOUNT_LOCKED"));
    }

    @Test
    @DisplayName("POST /api/auth/logout - success returns 200")
    void logout_Success() throws Exception {
        // Given
        LogoutRequest request = LogoutRequest.builder()
                .refreshToken("some_refresh_token")
                .build();

        // When/Then
        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Đăng xuất thành công"));
    }
}
