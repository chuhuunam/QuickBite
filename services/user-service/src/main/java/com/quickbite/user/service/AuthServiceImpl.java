package com.quickbite.user.service;

import com.quickbite.user.dto.AuthResponse;
import com.quickbite.user.dto.LoginRequest;
import com.quickbite.user.dto.RegisterRequest;
import com.quickbite.user.dto.UserResponse;
import com.quickbite.user.entity.User;
import com.quickbite.user.enums.UserRole;
import com.quickbite.user.enums.UserStatus;
import com.quickbite.user.exception.AccountLockedException;
import com.quickbite.user.exception.EmailAlreadyExistsException;
import com.quickbite.user.exception.InvalidCredentialsException;
import com.quickbite.user.exception.InvalidTokenException;
import com.quickbite.user.repository.UserRepository;
import com.quickbite.user.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @org.springframework.beans.factory.annotation.Value("${security.login.max-failed-attempts:5}")
    private int maxFailedAttempts;

    @org.springframework.beans.factory.annotation.Value("${security.login.lockout-duration-minutes:15}")
    private int lockoutDurationMinutes;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail().toLowerCase().trim();
        log.info("Registering user with email: {}", email);

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(UserRole.CUSTOMER)
                .build();

        user = userRepository.save(user);
        log.info("User registered successfully with id: {}", user.getId());

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(toUserResponse(user))
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().toLowerCase().trim();
        log.info("Login attempt");

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        checkAccountLocked(user);
        checkAccountStatus(user);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            handleFailedLogin(user);
            throw new InvalidCredentialsException();
        }

        resetFailedAttempts(user);
        log.info("Login successful for user id: {}", user.getId());

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(toUserResponse(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse refreshToken(String refreshToken) {
        log.info("Refresh token request");

        Long userId = jwtUtils.validateRefreshTokenAndGetUserId(refreshToken);
        if (userId == null) {
            throw new InvalidTokenException();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(InvalidTokenException::new);

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidTokenException();
        }

        String newAccessToken = jwtUtils.generateAccessToken(user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        log.info("Token refreshed for user id: {}", user.getId());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .user(toUserResponse(user))
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        log.info("Logout request received");
    }

    private void checkAccountLocked(User user) {
        if (user.getLockoutTime() != null && user.getLockoutTime().isAfter(LocalDateTime.now())) {
            throw new AccountLockedException(user.getLockoutTime());
        }

        if (user.getLockoutTime() != null && user.getLockoutTime().isBefore(LocalDateTime.now())) {
            resetFailedAttempts(user);
        }
    }

    private void checkAccountStatus(User user) {
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidCredentialsException();
        }
    }

    private void handleFailedLogin(User user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= maxFailedAttempts) {
            user.setLockoutTime(LocalDateTime.now().plusMinutes(lockoutDurationMinutes));
            log.warn("Account locked due to {} failed attempts", attempts);
        }

        userRepository.save(user);
    }

    private void resetFailedAttempts(User user) {
        if (user.getFailedLoginAttempts() > 0 || user.getLockoutTime() != null) {
            user.setFailedLoginAttempts(0);
            user.setLockoutTime(null);
            userRepository.save(user);
        }
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}
