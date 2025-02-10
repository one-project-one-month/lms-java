/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.config;

import lombok.RequiredArgsConstructor;
import org.oneProjectOneMonth.lms.security.exception.CustomAuthenticationEntryPoint;
import org.oneProjectOneMonth.lms.security.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String ROLE_ADMIN = ROLE_PREFIX + "ADMIN";
    private static final String ROLE_USER = ROLE_PREFIX + "USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(this::configureAuthorization)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .build();
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/public/**", "/api/v1/users/change-password").permitAll()

                .requestMatchers("/api/v1/users/**").access(hasRole(ROLE_USER))
                .requestMatchers("/api/v1/admin/**").access(hasRole(ROLE_ADMIN))

                .anyRequest().authenticated();
    }

    private AuthorizationManager<RequestAuthorizationContext> hasRole(String requiredRole) {
        return (Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) -> {
            HttpServletRequest request = context.getRequest();
            String userRole = getRoleFromToken(request);
            return new AuthorizationDecision(userRole.equals(requiredRole));
        };
    }

    private String getRoleFromToken(HttpServletRequest request) {
        String token = Optional.ofNullable(request.getHeader("Authorization"))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .orElse(null);

        if (token == null || token.isEmpty()) {
            return "";
        }

        return Optional.ofNullable(JwtUtil.decodeToken(token))
                .map(claims -> claims.get("role", String.class))
                .orElse("");
    }
}