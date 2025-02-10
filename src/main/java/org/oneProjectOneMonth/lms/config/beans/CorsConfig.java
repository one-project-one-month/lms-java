/*
 * @Author : Thant Htoo Aung
 * @Date : 1/13/2025
 * @Time : 10:07 AM
 */
package org.oneProjectOneMonth.lms.config.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${application.frontend-urls}")
    private String frontendUrlsCsv;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        List<String> allowedFrontendUrls = parseFrontendUrls(frontendUrlsCsv);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedFrontendUrls);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> parseFrontendUrls(String csv) {
        return List.of(csv.split("\\s*,\\s*"));
    }
}