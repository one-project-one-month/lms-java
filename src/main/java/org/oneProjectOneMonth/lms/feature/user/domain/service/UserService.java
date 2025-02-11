package org.oneProjectOneMonth.lms.feature.user.domain.service;

import org.oneProjectOneMonth.lms.feature.user.domain.dto.CreateUserRequest;

public interface UserService {
    Object retrieveUsers(int page, int limit) throws Exception;

    Object createUser(CreateUserRequest createUserRequest) throws Exception;

    void changePassword(String oldPassword, String newPassword, String authHeader) throws Exception;

    boolean usernameExists(String username);
}
