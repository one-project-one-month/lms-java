/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.UserDto;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.dto.RegisterRequest;

import java.util.Map;

public interface AuthService {
    Map<String, Object> authenticateUser(LoginRequest loginRequest);

//    ApiResponseDTO<Map<String, Object>> registerUser(RegisterRequest registerRequest);

    void logout(String accessToken);

    Map<String, Object> refreshToken(String refreshToken);

    CreateUserResponse getCurrentUser(String authHeader);
}
