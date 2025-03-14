package org.oneProjectOneMonth.lms.feature.user.domain.utils;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.instructor.domain.repository.InstructorRepository;
import org.oneProjectOneMonth.lms.feature.role.domain.model.RoleName;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.UserDto;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.oneProjectOneMonth.lms.feature.user.domain.repository.UserRepository;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.security.service.JwtService;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;

@Component
@Slf4j
public class UserUtil {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;

    public UserUtil(JwtService jwtService, UserRepository userRepository, InstructorRepository instructorRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
    }

    public CreateUserResponse getCurrentUserResponse(String authHeader) {
        String email = extractEmailFromToken(authHeader);
        User user = findUserByEmail(email);

        Instructor instructor = null;

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.INSTRUCTOR))) {
            instructor = instructorRepository.findByUser(user).orElse(null);
        }

        return CreateUserResponse.fromUser(user, instructor);
    }

    public String extractEmailFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Invalid Authorization header received");
            throw new SecurityException("Unauthorized: Missing or invalid token");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtService.validateToken(token);
        return claims.getSubject();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found for email: {}", email);
                    return new SecurityException("User not found");
                });
    }

    public String generateUniqueUsername(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }

        String baseUsername = normalizeUsername(fullName);
        String generatedUsername = baseUsername;

        int count = 1;
        while (userRepository.existsByUsername(generatedUsername)) {
            generatedUsername = baseUsername + count;
            count++;
        }

        return generatedUsername;
    }

    private String normalizeUsername(String fullName) {
        String normalized = Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("\\.+", "-")
                .replaceAll("^\\.|\\.$", "");

        return normalized.length() > 20 ? normalized.substring(0, 20) : normalized;
    }
}
