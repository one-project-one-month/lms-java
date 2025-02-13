/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponse;
import org.oneProjectOneMonth.lms.config.response.utils.ResponseUtil;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.dto.RefreshTokenData;
import org.oneProjectOneMonth.lms.security.dto.RegisterRequest;
import org.oneProjectOneMonth.lms.security.service.AuthService;
import org.oneProjectOneMonth.lms.security.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/${api.base.path}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    public final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Received login attempt for email: {}", loginRequest.getEmail());

        ApiResponse response = authService.authenticateUser(loginRequest);

        if (response.getSuccess() == 1) {
            log.info("Login successful for user: {}", loginRequest.getEmail());
        } else {
            log.warn("Login failed for user: {}", loginRequest.getEmail());
        }

        return ResponseUtil.buildResponse(request, response, 0L);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            HttpServletRequest request) {
        log.info("Received logout request");

        if ((accessToken == null || !accessToken.startsWith("Bearer "))) {

            log.warn("Invalid or missing tokens in logout request");
            throw new SecurityException("Invalid or missing authorization tokens.");
        }

        try {
            authService.logout(accessToken);
            ApiResponse response = ApiResponse.builder()
                    .success(1)
                    .code(200)
                    .data(true)
                    .message("Logout successful")
                    .build();

            log.info("User logged out successfully");

            return ResponseUtil.buildResponse(request, response, 0L);
        } catch (SecurityException ex) {
            log.warn("Logout failed due to security reasons: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during logout", ex);
            throw new RuntimeException("An error occurred during logout.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Validated @RequestBody RegisterRequest registerRequest,
            HttpServletRequest request) {
        log.info("Received registration request for email: {}", registerRequest.getEmail());

        ApiResponse response = authService.registerUser(registerRequest);

        if (response.getSuccess() == 1) {
            log.info("User registered successfully: {}", registerRequest.getEmail());
        } else {
            log.warn("Registration failed for email: {}", registerRequest.getEmail());
        }

        return ResponseUtil.buildResponse(request, response, 0L);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refresh(@Validated @RequestBody RefreshTokenData refreshTokenData,
            HttpServletRequest request) {
        log.info("Received token refresh request");

        ApiResponse response = authService.refreshToken(refreshTokenData.getRefreshToken());

        if (response.getSuccess() == 1) {
            log.info("Token refreshed successfully");
        } else {
            log.warn("Token refresh failed");
        }

        return ResponseUtil.buildResponse(request, response, 0L);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser(@RequestHeader("Authorization") String authHeader,
            HttpServletRequest request) {
        log.info("Fetching current authenticated user");

        double requestStartTime = System.currentTimeMillis();
        ApiResponse response = authService.getCurrentUser(authHeader);

        return ResponseUtil.buildResponse(request, response, requestStartTime);
    }
}
