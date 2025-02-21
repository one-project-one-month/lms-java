package org.oneProjectOneMonth.lms.feature.user.domain.service;

import java.util.List;

import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.request.UpdateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.springframework.validation.BindingResult;

public interface UserService {

    CreateUserResponse signUp(CreateUserRequest request);
    
    CreateUserResponse updateUser(UpdateUserRequest request,Long id,BindingResult bindingResult);
    
    List<CreateUserResponse> getAllUsers();

	void changePassword(String oldPassword, String newPassword, String authHeader) throws Exception;
	
	void deactivateUser(Long id);
	
	boolean usernameExists(String username);
}
