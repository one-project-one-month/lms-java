/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service.impl;

import io.jsonwebtoken.Claims;
import org.oneProjectOneMonth.lms.security.service.JwtService;
import org.oneProjectOneMonth.lms.security.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtServiceImpl implements JwtService {

    private final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();

    @Override
    public Claims validateToken(String token) {
        if (!JwtUtil.isTokenValid(token)) {
            throw new SecurityException("Invalid or expired token.");
        }

        if (isTokenRevoked(token)) {
            throw new SecurityException("Token has been revoked.");
        }

        return JwtUtil.decodeToken(token);
    }

    @Override
    public void revokeToken(String token) {
        revokedTokens.add(token);
    }

    private boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }

    @Override
    public String generateToken(Map<String, Object> claims, String roleName, String subject, long expirationMillis) {
        return JwtUtil.generateToken(claims, roleName, subject, expirationMillis);
    }
}