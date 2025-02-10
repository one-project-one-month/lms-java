/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponse;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.dto.RegisterRequest;

public interface AuthService {
    ApiResponse authenticateUser(LoginRequest loginRequest);

    ApiResponse registerUser(RegisterRequest registerRequest);

    void logout(String accessToken);

    ApiResponse refreshToken(String refreshToken);

    ApiResponse getCurrentUser(String authHeader);
}
