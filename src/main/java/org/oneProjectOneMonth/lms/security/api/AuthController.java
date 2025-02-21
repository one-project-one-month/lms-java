/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.service.UserService;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.dto.RefreshTokenData;
import org.oneProjectOneMonth.lms.security.service.AuthService;
import org.oneProjectOneMonth.lms.security.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.base.path}/${api.auth.base.path}")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    public final UserService userService;
    public final JwtService jwtService;

    @PostMapping("/${api.auth.login}")
    public ResponseEntity<ApiResponseDTO<Object>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Received login attempt for email: {}", loginRequest.getEmail());

        Object responseData = authService.authenticateUser(loginRequest);

        log.info(responseData != null ? "Login successful for user: {}" : "Login failed for user: {}", loginRequest.getEmail());
        return ResponseEntity.ok(new ApiResponseDTO<>(responseData, responseData != null ? "Login successful" : "Login failed"));
    }

    @PostMapping("/${api.auth.logout}")
    public ResponseEntity<ApiResponseDTO<Boolean>> logout(
            @RequestHeader(value = "Authorization", required = false) String accessToken) {
        log.info("Received logout request");

        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            log.warn("Invalid or missing tokens in logout request");
            throw new SecurityException("Invalid or missing authorization tokens.");
        }

        try {
            authService.logout(accessToken);
            log.info("User logged out successfully");
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Logout successful"));
        } catch (SecurityException ex) {
            log.warn("Logout failed due to security reasons: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during logout", ex);
            throw new RuntimeException("An error occurred during logout.");
        }
    }

    @PostMapping("/${api.auth.register}")
    public ResponseEntity<ApiResponseDTO<Object>> register(
            @Valid @RequestBody CreateUserRequest registerRequest
    ) {
        log.info("Received registration request for email: {}", registerRequest.email());

        Object registeredUser = userService.signUp(registerRequest);

        log.info(registeredUser != null ? "User registered successfully: {}" : "Registration failed for email: {}", registerRequest.email());
        return ResponseEntity.ok(new ApiResponseDTO<>(registeredUser, registeredUser != null ? "Registration successful" : "Registration failed"));
    }

    @PostMapping("/${api.auth.token-refresh}")
    public ResponseEntity<ApiResponseDTO<Object>> refresh(@Validated @RequestBody RefreshTokenData refreshTokenData) {
        log.info("Received token refresh request");

        Object refreshedToken = authService.refreshToken(refreshTokenData.getRefreshToken());

        log.info(refreshedToken != null ? "Token refreshed successfully" : "Token refresh failed");
        return ResponseEntity.ok(new ApiResponseDTO<>(refreshedToken, refreshedToken != null ? "Token refreshed successfully" : "Token refresh failed"));
    }

    @GetMapping("/${api.auth.get-current-user}")
    public ResponseEntity<ApiResponseDTO<Object>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        log.info("Fetching current authenticated user");

        Object currentUser = authService.getCurrentUser(authHeader);
        return ResponseEntity.ok(new ApiResponseDTO<>(currentUser, "Fetched current user successfully"));
    }
}
