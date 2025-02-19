package org.oneProjectOneMonth.lms.feature.user.domain.service;

import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;

import java.util.List;

public interface UserService {

    CreateUserResponse signUp(CreateUserRequest request);

    List<CreateUserResponse> getAllUsers();

	void changePassword(String oldPassword, String newPassword, String authHeader) throws Exception;

	boolean usernameExists(String username);
}
