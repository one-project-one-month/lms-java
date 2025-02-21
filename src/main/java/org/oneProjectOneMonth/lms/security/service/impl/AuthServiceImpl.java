/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.role.domain.model.Role;
import org.oneProjectOneMonth.lms.feature.role.domain.model.RoleName;
import org.oneProjectOneMonth.lms.feature.role.domain.repository.RoleRepository;
import org.oneProjectOneMonth.lms.feature.token.domain.dto.TokenDto;
import org.oneProjectOneMonth.lms.feature.token.domain.model.Token;
import org.oneProjectOneMonth.lms.feature.token.domain.repository.TokenRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.UserDto;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.oneProjectOneMonth.lms.feature.user.domain.repository.UserRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.UserUtil;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.security.dto.LoginRequest;
import org.oneProjectOneMonth.lms.security.dto.RegisterRequest;
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
    public ApiResponseDTO<Map<String, Object>> authenticateUser(LoginRequest loginRequest) {
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
            return new ApiResponseDTO<>("UNAUTHORIZED", "Invalid email or password");
        }

        log.info("User authenticated successfully: {}", loginRequest.getEmail());

        UserDto userDto = DtoUtil.map(user, UserDto.class, modelMapper);

        Token refreshToken = tokenRepository.findByUser(user)
                .orElseThrow(() -> {
                    log.warn("Token not found for user: {}", loginRequest.getEmail());
                    return new SecurityException("Token not found for user");
                });

        Map<String, Object> tokenData = generateTokens(user, roleName);

        TokenDto tokenDto = DtoUtil.map(refreshToken, TokenDto.class, modelMapper);

        return new ApiResponseDTO<>(
                Map.of(
                        "user", userDto,
                        "accessToken", tokenData.get("accessToken"),
                        "refreshToken", tokenDto.getRefreshtoken()
                ),
                "You are successfully logged in!"
        );
    }

    @Override
    @Transactional
    public ApiResponseDTO<Map<String, Object>> registerUser(RegisterRequest registerRequest) {
        log.info("Registering new user with email: {}", registerRequest.getEmail());

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            log.warn("Email already exists: {}", registerRequest.getEmail());
            return new ApiResponseDTO<>("CONFLICT", "Email is already in use", null);
        }

        Role userRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("Role not found in database!"));
        log.info("Assigning role: {}", userRole.getName());

        User newUser = User.builder()
                .name(registerRequest.getName())
                .username(userUtil.generateUniqueUsername(registerRequest.getName()))
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .address(registerRequest.getAddress())
                .dob(registerRequest.getDob())
                .available(true)
                .phone(registerRequest.getPhone())
                .profilePhoto(registerRequest.getProfilePhoto())
                .roles(Set.of(userRole))
                .build();

        userRepository.save(newUser);

        Map<String, Object> tokenData = generateTokens(newUser, String.valueOf(userRole.getName()));

        String accessToken = (String) tokenData.get("accessToken");
        String refreshToken = (String) tokenData.get("refreshToken");

        Instant expiredAt = Instant.now().plus(7, ChronoUnit.DAYS);

        Token token = Token.builder()
                .user(newUser)
                .refreshtoken(refreshToken)
                .expiredAt(expiredAt)
                .build();

        tokenRepository.save(token);

        log.info("User registered successfully: {}", registerRequest.getEmail());

        UserDto userDto = DtoUtil.map(newUser, UserDto.class, modelMapper);

        return new ApiResponseDTO<>(
                Map.of(
                        "user", userDto,
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                ),
                "You have registered successfully."
        );
    }

    private Map<String, Object> generateTokens(User user, String roleName) {
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
    public ApiResponseDTO<Map<String, Object>> refreshToken(String refreshToken) {
        log.info("Validating refresh token");

        Claims claims;
        try {
            claims = jwtService.validateToken(refreshToken);
        } catch (SecurityException ex) {
            log.warn("Invalid refresh token: {}", ex.getMessage());
            return new ApiResponseDTO<>("UNAUTHORIZED", "Invalid or expired refresh token", null);
        }

        String email = claims.getSubject();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            log.warn("User not found for refresh token: {}", email);
            return new ApiResponseDTO<>("UNAUTHORIZED", "User not found", null);
        }

        log.info("Generating new access token for user: {}", email);

        Set<Role> roleList = user.getRoles();
        String roleName = roleList.stream()
                .map(role -> role.getName().name())
                .findFirst()
                .orElse("ROLE_USER");

        String newAccessToken = jwtService.generateToken(ClaimsProvider.generateClaims(user), roleName, email,
                15 * 60 * 1000);

        return new ApiResponseDTO<>(
                Map.of("accessToken", newAccessToken),
                "Access token refreshed successfully"
        );
    }

    @Override
    public ApiResponseDTO<Map<String, Object>> getCurrentUser(String authHeader) {
        UserDto userDto = userUtil.getCurrentUserDto(authHeader);

        return new ApiResponseDTO<>(
                Map.of("user", userDto),
                "User retrieved successfully"
        );
    }

}
