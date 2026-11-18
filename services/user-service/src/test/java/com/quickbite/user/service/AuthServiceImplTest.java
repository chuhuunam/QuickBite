package com.quickbite.user.service;

import com.quickbite.user.dto.AuthResponse;
import com.quickbite.user.dto.LoginRequest;
import com.quickbite.user.dto.RegisterRequest;
import com.quickbite.user.entity.User;
import com.quickbite.user.enums.UserRole;
import com.quickbite.user.enums.UserStatus;
import com.quickbite.user.exception.AccountLockedException;
import com.quickbite.user.exception.EmailAlreadyExistsException;
import com.quickbite.user.exception.InvalidCredentialsException;
import com.quickbite.user.repository.UserRepository;
import com.quickbite.user.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest validRequest;
    private LoginRequest loginRequest;
    private User existingUser;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "maxFailedAttempts", 5);
        ReflectionTestUtils.setField(authService, "lockoutDurationMinutes", 15);

        validRequest = RegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .fullName("Test User")
                .build();

        loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        existingUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encoded_password")
                .fullName("Test User")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .failedLoginAttempts(0)
                .build();
    }

    @Test
    @DisplayName("register - should create user and return tokens")
    void register_Success() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(jwtUtils.generateAccessToken(any(User.class))).thenReturn("access_token");
        when(jwtUtils.generateRefreshToken(any(User.class))).thenReturn("refresh_token");

        // When
        AuthResponse response = authService.register(validRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access_token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh_token");
        assertThat(response.getUser().getEmail()).isEqualTo("test@example.com");
        assertThat(response.getUser().getRole()).isEqualTo(UserRole.CUSTOMER);

        // Verify password was encoded
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("register - should normalize email to lowercase")
    void register_NormalizesEmail() {
        // Given
        validRequest.setEmail("TEST@EXAMPLE.COM");
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(jwtUtils.generateAccessToken(any())).thenReturn("token");
        when(jwtUtils.generateRefreshToken(any())).thenReturn("refresh");

        // When
        authService.register(validRequest);

        // Then
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsByEmail(emailCaptor.capture());
        assertThat(emailCaptor.getValue()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("register - should throw when email exists")
    void register_EmailExists_ThrowsException() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When/Then
        assertThatThrownBy(() -> authService.register(validRequest))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("test@example.com");

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("register - should hash password with BCrypt")
    void register_HashesPassword() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$hashedvalue");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(jwtUtils.generateAccessToken(any())).thenReturn("token");
        when(jwtUtils.generateRefreshToken(any())).thenReturn("refresh");

        // When
        authService.register(validRequest);

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("$2a$10$hashedvalue");
    }

    @Test
    @DisplayName("login - should return tokens on success")
    void login_Success() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(jwtUtils.generateAccessToken(existingUser)).thenReturn("access_token");
        when(jwtUtils.generateRefreshToken(existingUser)).thenReturn("refresh_token");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access_token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh_token");
        assertThat(response.getUser().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("login - should throw when email not found")
    void login_EmailNotFound_ThrowsInvalidCredentials() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Email hoặc mật khẩu không đúng");
    }

    @Test
    @DisplayName("login - should throw when password wrong")
    void login_WrongPassword_ThrowsInvalidCredentials() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When/Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("login - should increment failed attempts on wrong password")
    void login_WrongPassword_IncrementsFailedAttempts() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When
        try {
            authService.login(loginRequest);
        } catch (InvalidCredentialsException ignored) {}

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getFailedLoginAttempts()).isEqualTo(1);
    }

    @Test
    @DisplayName("login - should lock account after 5 failed attempts")
    void login_FiveFailedAttempts_LocksAccount() {
        // Given
        existingUser.setFailedLoginAttempts(4);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When
        try {
            authService.login(loginRequest);
        } catch (InvalidCredentialsException ignored) {}

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getFailedLoginAttempts()).isEqualTo(5);
        assertThat(userCaptor.getValue().getLockoutTime()).isNotNull();
        assertThat(userCaptor.getValue().getLockoutTime()).isAfter(LocalDateTime.now());
    }

    @Test
    @DisplayName("login - should throw when account is locked")
    void login_AccountLocked_ThrowsAccountLockedException() {
        // Given
        existingUser.setLockoutTime(LocalDateTime.now().plusMinutes(10));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        // When/Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AccountLockedException.class);

        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("login - should allow login after lockout expires")
    void login_LockoutExpired_AllowsLogin() {
        // Given
        existingUser.setLockoutTime(LocalDateTime.now().minusMinutes(1));
        existingUser.setFailedLoginAttempts(5);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(jwtUtils.generateAccessToken(any())).thenReturn("token");
        when(jwtUtils.generateRefreshToken(any())).thenReturn("refresh");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response).isNotNull();
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, atLeast(1)).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getFailedLoginAttempts()).isEqualTo(0);
        assertThat(userCaptor.getValue().getLockoutTime()).isNull();
    }

    @Test
    @DisplayName("login - should reset failed attempts on success")
    void login_Success_ResetsFailedAttempts() {
        // Given
        existingUser.setFailedLoginAttempts(3);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(jwtUtils.generateAccessToken(any())).thenReturn("token");
        when(jwtUtils.generateRefreshToken(any())).thenReturn("refresh");

        // When
        authService.login(loginRequest);

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getFailedLoginAttempts()).isEqualTo(0);
    }
}
