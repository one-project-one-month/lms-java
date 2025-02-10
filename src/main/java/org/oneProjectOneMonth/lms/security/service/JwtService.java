/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {
    Claims validateToken(String token);

    void revokeToken(String token);

    String generateToken(Map<String, Object> claims, String roleName, String subject, long expirationMillis);
}