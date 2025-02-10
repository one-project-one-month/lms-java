package org.oneProjectOneMonth.lms.api.user.service;

import org.oneProjectOneMonth.lms.api.user.dto.CreateUserRequest;

public interface UserService {
    Object retrieveUsers(int page, int limit) throws Exception;

    Object createUser(CreateUserRequest createUserRequest) throws Exception;

    void changePassword(String oldPassword, String newPassword, String authHeader) throws Exception;

    boolean usernameExists(String username);
}
