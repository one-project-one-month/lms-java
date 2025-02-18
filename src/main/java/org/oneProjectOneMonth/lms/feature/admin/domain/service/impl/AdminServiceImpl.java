package org.oneProjectOneMonth.lms.feature.admin.domain.service.impl;

import java.util.Optional;

import org.oneProjectOneMonth.lms.feature.admin.domain.dto.UpdateUserRequest;
import org.oneProjectOneMonth.lms.feature.admin.domain.service.AdminService;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.instructor.domain.repository.InstructorRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.oneProjectOneMonth.lms.feature.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
	private final UserRepository userRepository;
	private final InstructorRepository instructorRepository;

	@Override
	@Transactional
	public UpdateUserRequest updateUser(UpdateUserRequest updatedUser, Long id) {
		User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found for id "+id));
		updateUserProperties(user, updatedUser);
		userRepository.save(user);

		Optional<Instructor> instructorOptional = instructorRepository.findInstructorByUserId(id);
		if (instructorOptional.isPresent()) {
			Instructor instructor = instructorOptional.get();
			updateInstructorProperties(instructor, updatedUser);
			instructorRepository.save(instructor);
		}
		return updatedUser;
	}

	private void updateUserProperties(User user, UpdateUserRequest updatedUser) {
		if (updatedUser.getName() != null) {
			user.setName(updatedUser.getName());
		}
		if (updatedUser.getEmail() != null) {
			user.setEmail(updatedUser.getEmail());
		}
		if (updatedUser.getDob() != null) {
			user.setDob(updatedUser.getDob());
		}
		if (updatedUser.getAddress() != null) {
			user.setAddress(updatedUser.getAddress());
		}
		if (updatedUser.getPhone() != null) {
			user.setPhone(updatedUser.getPhone());
		}
		if (updatedUser.getProfilePhoto() != null) {
			user.setProfilePhoto(updatedUser.getProfilePhoto());
		}
	}

	private void updateInstructorProperties(Instructor instructor, UpdateUserRequest updatedUser) {
		if (updatedUser.getNrc() != null) {
			instructor.setNrc(updatedUser.getNrc());
		}
		if (updatedUser.getEduBackground() != null) {
			instructor.setEduBackground(updatedUser.getEduBackground());
		}
	}

	@Override
	public void deactivateUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found for id "+id));
		user.setAvailable(false);
		userRepository.save(user);
	}
}
