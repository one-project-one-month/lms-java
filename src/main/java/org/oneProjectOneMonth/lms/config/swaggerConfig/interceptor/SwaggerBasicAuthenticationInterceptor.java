package org.oneProjectOneMonth.lms.config.swaggerConfig.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.oneProjectOneMonth.lms.config.Encryptor.JasyptEncryptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SwaggerBasicAuthenticationInterceptor  implements HandlerInterceptor {

    @Value("${swagger.username}")
    private String swaggerUserName;

    @Value("${swagger.password}")
    private String swaggerUserPassword;

    @Autowired
    private JasyptEncryptorConfig jasyptEncryptorConfig;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, 
                           @NonNull HttpServletResponse response, 
                           @NonNull Object handler) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return username.equals(swaggerUserName)
                && swaggerUserPassword.equals(jasyptEncryptorConfig.decryptMessage(password));
    }
}
