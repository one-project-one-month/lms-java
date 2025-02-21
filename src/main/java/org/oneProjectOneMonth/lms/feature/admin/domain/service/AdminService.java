package org.oneProjectOneMonth.lms.feature.admin.domain.service;

import org.oneProjectOneMonth.lms.feature.admin.domain.dto.UpdateUserRequest;

public interface AdminService {
	UpdateUserRequest updateUser(UpdateUserRequest updateUser, Long id);
	
	void deactivateUser(Long id);
}
