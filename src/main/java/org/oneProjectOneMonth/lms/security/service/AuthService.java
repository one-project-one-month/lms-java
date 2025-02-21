/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.dto.RegisterRequest;

import java.util.Map;

public interface AuthService {
    ApiResponseDTO<Map<String, Object>> authenticateUser(LoginRequest loginRequest);

    ApiResponseDTO<Map<String, Object>> registerUser(RegisterRequest registerRequest);

    void logout(String accessToken);

    ApiResponseDTO<Map<String, Object>> refreshToken(String refreshToken);

    ApiResponseDTO<Map<String, Object>> getCurrentUser(String authHeader);
}
