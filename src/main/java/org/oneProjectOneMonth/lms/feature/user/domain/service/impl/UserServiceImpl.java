package org.oneProjectOneMonth.lms.feature.user.domain.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.feature.admin.domain.model.Admin;
import org.oneProjectOneMonth.lms.feature.admin.domain.repository.AdminRepository;
import org.oneProjectOneMonth.lms.config.utils.EntityUtil;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.instructor.domain.repository.InstructorRepository;
import org.oneProjectOneMonth.lms.feature.role.domain.model.Role;
import org.oneProjectOneMonth.lms.feature.role.domain.model.RoleName;
import org.oneProjectOneMonth.lms.feature.role.domain.repository.RoleRepository;
import org.oneProjectOneMonth.lms.feature.student.domain.model.Student;
import org.oneProjectOneMonth.lms.feature.student.domain.repository.StudentRepository;
import org.oneProjectOneMonth.lms.feature.token.domain.model.Token;
import org.oneProjectOneMonth.lms.feature.token.domain.repository.TokenRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;

import org.oneProjectOneMonth.lms.feature.user.domain.dto.UserDto;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.oneProjectOneMonth.lms.feature.user.domain.repository.UserRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.request.UpdateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.feature.user.domain.service.UserService;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.PasswordValidatorUtil;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.UserUtil;
import org.oneProjectOneMonth.lms.security.service.impl.AuthServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final AdminRepository adminRepository;
    private final AuthServiceImpl  authService;
    private final TokenRepository tokenRepository;

	@Override
	public CreateUserResponse signUp(CreateUserRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email already exists.");
		}

		if (userRepository.existsByPhone(request.phone())) {
			throw new IllegalArgumentException("Phone number already exists.");
		}

		if (userRepository.existsByUsername(request.username())) {
			throw new IllegalArgumentException("Username already exists.");
		}

		if (request.nrc() != null && instructorRepository.existsByNrc(request.nrc())) {
			throw new IllegalArgumentException("NRC already exists.");
		}

		Role role = roleRepository.findByName(RoleName.valueOf(request.roles().toUpperCase()))
				.orElseThrow(() -> new IllegalArgumentException("Role not found: " + request.roles()));

		Set<Role> roles = Set.of(role);
		User user = modelMapper.map(request, User.class);
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRoles(roles);

		User savedUser = userRepository.save(user);
		log.info("User created successfully: {}", savedUser);
		Instructor instructor = null;

		if(role.getName().equals(RoleName.ADMIN)){
			Admin admin = new Admin();
			user.setAvailable(true);
			adminRepository.save(admin);
		}else if (role.getName().equals(RoleName.STUDENT)) {
			Student student = new Student();
			user.setAvailable(true);
			student.setUser(savedUser);
			studentRepository.save(student);
		} else if (role.getName().equals(RoleName.INSTRUCTOR)) {
			if (request.nrc() == null || request.eduBackground() == null) {
				throw new IllegalArgumentException("NRC and Education Background are required for Instructors.");
			}
			instructor = new Instructor();
			instructor.setUser(savedUser);
			user.setAvailable(false);
			instructor.setNrc(request.nrc());
			instructor.setEduBackground(request.eduBackground());
			instructorRepository.save(instructor);
		}

		// Generate access and refresh tokens
		Map<String, Object> tokens = authService.generateTokens(savedUser, role.getName().name());
		String refreshToken = tokens.get("refreshToken").toString();

		Token token = new Token();
		token.setRefreshtoken(refreshToken);
		token.setExpiredAt(Instant.now().plus(7, ChronoUnit.DAYS));
		token.setUser(savedUser);
		tokenRepository.save(token);
		return CreateUserResponse.fromUser(savedUser, instructor);
	}

	@Override
	public List<CreateUserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();

		return users.stream().map(user -> {
			Instructor instructor = null;

			if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.INSTRUCTOR))) {
				instructor = instructorRepository.findByUser(user).orElse(null);
			}

			return CreateUserResponse.fromUser(user, instructor);
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void changePassword(String oldPassword, String newPassword, String authHeader) throws Exception {
		log.info("Initiating password change for authenticated user.");

		UserDto userDto = userUtil.getCurrentUserDto(authHeader);
		User currentUser = EntityUtil.getEntityById(userRepository, userDto.getId());

		if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
			log.warn("Password change failed: Incorrect old password for user ID {}", currentUser.getId());
			throw new IllegalArgumentException("Incorrect old password.");
		}

		if (!PasswordValidatorUtil.isValid(newPassword)) {
			log.warn("Password change failed: Weak password provided.");
			throw new IllegalArgumentException("Password does not meet security requirements.");
		}

		currentUser.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(currentUser);

		log.info("Password changed successfully for user ID {}", currentUser.getId());
	}

	@Override
	public boolean usernameExists(String username) {
		return userRepository.countByUsername(username) > 0;
	}

	@Override
	@Transactional
	public CreateUserResponse updateUser(UpdateUserRequest request, Long id, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			throw new IllegalArgumentException(errors.toString());
		}
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found for id " + id));
		if (request.getName() != null && request.getName().isBlank()) {
			throw new IllegalArgumentException("Name cannot be empty.");
		}
		if(request.getUsername() != null && request.getUsername().isBlank()) {
			throw new IllegalArgumentException("User name cannot be empty.");
		}
		if (request.getEmail() != null && request.getEmail().isBlank()) {
			throw new IllegalArgumentException("Email cannot be empty.");
		}
		if (request.getAddress() != null && request.getAddress().isBlank()) {
			throw new IllegalArgumentException("Address cannot be empty.");
		}
		if (request.getPhone() != null && request.getPhone().isBlank()) {
			throw new IllegalArgumentException("Phone number cannot be empty.");
		}
		if (request.getProfilePhoto() != null && request.getProfilePhoto().isBlank()) {
			throw new IllegalArgumentException("Profile Photo cannot be empty.");
		}

		if (request.getNrc() != null && request.getNrc().isBlank()) {
			throw new IllegalArgumentException("NRC cannot be empty.");
		}

		if (request.getEduBackground() != null && request.getEduBackground().isBlank()) {
			throw new IllegalArgumentException("Education Background cannot be empty.");
		}

		updateUserProperties(user, request);
		userRepository.save(user);

		Optional<Instructor> instructorOptional = instructorRepository.findInstructorByUserId(id);
		if (instructorOptional.isPresent()) {
			Instructor instructor = instructorOptional.get();
			updateInstructorProperties(instructor, request);
			instructorRepository.save(instructor);
			return CreateUserResponse.fromUser(user, instructor);
		}
		return CreateUserResponse.fromUser(user, null);
	}

	private void updateUserProperties(User user, UpdateUserRequest request) {
		if (request.getName() != null) {
			if (userRepository.existsByUsername(request.getName())) {
				throw new IllegalArgumentException("Name already exists.");
			}
			user.setName(request.getName());
		}
		if(request.getUsername() != null) {
			if (userRepository.existsByUsername(request.getUsername())) {
				throw new IllegalArgumentException("Username already exists.");
			}
			user.setUsername(request.getUsername());
		}
		if (request.getEmail() != null) {
			if (userRepository.existsByEmail(request.getEmail())) {
				throw new IllegalArgumentException("Email already exists.");
			}
			user.setEmail(request.getEmail());
		}
		if (request.getDob() != null) {
			user.setDob(request.getDob());
		}
		if (request.getAddress() != null) {
			user.setAddress(request.getAddress());
		}
		if (request.getPhone() != null) {
			if (userRepository.existsByPhone(request.getPhone())) {
				throw new IllegalArgumentException("Phone number already exists.");
			}
			user.setPhone(request.getPhone());
		}
		if (request.getProfilePhoto() != null) {
			user.setProfilePhoto(request.getProfilePhoto());
		}
	}

	private void updateInstructorProperties(Instructor instructor, UpdateUserRequest request) {
		if (request.getNrc() != null) {
			instructor.setNrc(request.getNrc());
		}
		if (request.getEduBackground() != null) {
			if (userRepository.existsByUsername(request.getName())) {
				throw new IllegalArgumentException("Username already exists.");
			}
			instructor.setEduBackground(request.getEduBackground());
		}
	}
	
	@Override
	public void deactivateUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found for id "+id));
		user.setAvailable(false);
		userRepository.save(user);
	}

}
