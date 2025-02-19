package org.oneProjectOneMonth.lms.feature.user.domain.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.utils.EntityUtil;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.instructor.domain.repository.InstructorRepository;
import org.oneProjectOneMonth.lms.feature.role.domain.model.Role;
import org.oneProjectOneMonth.lms.feature.role.domain.model.RoleName;
import org.oneProjectOneMonth.lms.feature.role.domain.repository.RoleRepository;
import org.oneProjectOneMonth.lms.feature.student.domain.model.Student;
import org.oneProjectOneMonth.lms.feature.student.domain.repository.StudentRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.UserDto;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.oneProjectOneMonth.lms.feature.user.domain.repository.UserRepository;
import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.feature.user.domain.service.UserService;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.PasswordValidatorUtil;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.UserUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user.setRoleId(role.getId());

        // Directly Save ADMIN Role
        if (role.getName().equals(RoleName.ADMIN)) {
            User savedAdmin = userRepository.save(user);
            log.info("Admin user created successfully: {}", savedAdmin);
            return CreateUserResponse.fromUser(savedAdmin, null);
        }

        User savedUser = userRepository.save(user);
        log.info("User created successfully: {}", savedUser);
        Instructor instructor = null;

        if (role.getName().equals(RoleName.STUDENT)) {
            Student student = new Student();
            student.setUser(savedUser);
            studentRepository.save(student);
        } else if (role.getName().equals(RoleName.INSTRUCTOR)) {
            if (request.nrc() == null || request.eduBackground() == null) {
                throw new IllegalArgumentException("NRC and Education Background are required for Instructors.");
            }
            instructor = new Instructor();
            instructor.setUser(savedUser);
            instructor.setNrc(request.nrc());
            instructor.setEduBackground(request.eduBackground());
            instructorRepository.save(instructor);
        }
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

	
}
