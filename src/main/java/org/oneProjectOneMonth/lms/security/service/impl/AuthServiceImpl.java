/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.role.domain.model.Role;
import org.oneProjectOneMonth.lms.feature.role.domain.repository.RoleRepository;
import org.oneProjectOneMonth.lms.feature.token.domain.dto.TokenDto;
import org.oneProjectOneMonth.lms.feature.token.domain.model.Token;
import org.oneProjectOneMonth.lms.feature.token.domain.repository.TokenRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.UserDto;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.oneProjectOneMonth.lms.feature.user.domain.repository.UserRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.UserUtil;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.service.AuthService;
import org.oneProjectOneMonth.lms.security.service.JwtService;
import org.oneProjectOneMonth.lms.security.utils.ClaimsProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserUtil userUtil;
    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> authenticateUser(LoginRequest loginRequest) {
        log.info("Authenticating user with email: {}", loginRequest.getEmail());

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("User not found: {}", loginRequest.getEmail());
                    return new SecurityException("Invalid email or password");
                });

        Set<Role> roleList = user.getRoles();
        String roleName = roleList.stream()
                .map(role -> role.getName().name())
                .findFirst()
                .orElse("ADMIN");

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user: {}", loginRequest.getEmail());
            throw new SecurityException("Invalid email or password");
        }

        log.info("User authenticated successfully: {}", loginRequest.getEmail());

        Token refreshToken = tokenRepository.findByUser(user)
                .orElseThrow(() -> {
                    log.warn("Token not found for user: {}", loginRequest.getEmail());
                    return new SecurityException("Token not found for user");
                });

        Map<String, Object> tokenData = generateTokens(user, roleName);

        TokenDto tokenDto = DtoUtil.map(refreshToken, TokenDto.class, modelMapper);

        return Map.of(
                "accessToken", tokenData.get("accessToken"),
                "refreshToken", tokenDto.getRefreshtoken()
        );
    }

    public Map<String, Object> generateTokens(User user, String roleName) {
        log.debug("Generating tokens for user: {}", user.getEmail());

        String accessToken = jwtService.generateToken(ClaimsProvider.generateClaims(user), roleName,
                user.getEmail(), 15 * 60 * 1000);
        String refreshToken = jwtService.generateToken(ClaimsProvider.generateClaims(user), roleName,
                user.getEmail(), 7 * 24 * 60 * 60 * 1000);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    @Override
    public void logout(String accessToken) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            String token = accessToken.substring(7);
            Claims claims = jwtService.validateToken(token);
            String userEmail = claims.getSubject();

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new SecurityException(
                            "User not found. Cannot proceed with logout."));

            log.debug("Revoking access token for user: {}", user.getEmail());
            jwtService.revokeToken(token);
        }

        log.info("User successfully logged out.");
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken) {
        log.info("Validating refresh token");

        Claims claims;
        try {
            claims = jwtService.validateToken(refreshToken);
        } catch (SecurityException ex) {
            log.warn("Invalid refresh token: {}", ex.getMessage());
            throw new SecurityException("Invalid or expired refresh token");
        }

        String email = claims.getSubject();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            log.warn("User not found for refresh token: {}", email);
            throw new SecurityException("User not found");
        }

        log.info("Generating new access token for user: {}", email);

        Set<Role> roleList = user.getRoles();
        String roleName = roleList.stream()
                .map(role -> role.getName().name())
                .findFirst()
                .orElse("ADMIN");

        Map<String, Object> newTokens = generateTokens(user, roleName);
        String newAccessToken = newTokens.get("accessToken").toString();
        String newRefreshToken = newTokens.get("refreshToken").toString();

        Token existingToken = tokenRepository.findByUser(user)
                .orElseThrow(() -> new SecurityException("Token not found for user"));

        existingToken.setRefreshtoken(newRefreshToken);
        existingToken.setExpiredAt(Instant.now().plus(7, ChronoUnit.DAYS));
        tokenRepository.save(existingToken);

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        );
    }

    @Override
    public CreateUserResponse getCurrentUser(String authHeader) {

        return userUtil.getCurrentUserResponse(authHeader);
    }

}
