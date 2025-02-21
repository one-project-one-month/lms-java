package org.oneProjectOneMonth.lms.feature.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.config.response.utils.ResponseUtil;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.ChangePasswordRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.request.UpdateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.feature.user.domain.service.UserService;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.PasswordValidatorUtil;
import org.oneProjectOneMonth.lms.config.response.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/${api.base.path}/${api.user.base.path}")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User", description = "User Api")
public class UserController {

	private final UserService userService;

    @PostMapping
    @Operation(summary = "User Signup Api")
     public ResponseEntity<ApiResponseDTO<CreateUserResponse>> signUp(
             @Valid @RequestBody CreateUserRequest request
    ){
        return ResponseEntity.ok(new ApiResponseDTO<>(userService.signUp(request)));
    }

    @GetMapping
    @Operation(summary = "Get All Users Api")
    public ResponseEntity<ApiResponseDTO<List<CreateUserResponse>>> getAllUsers() {
        List<CreateUserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponseDTO<>(users));
    }

	@PostMapping("/${api.user.change-password}")
	public ResponseEntity<ApiResponseDTO<Boolean>> changePassword(
			@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
			@RequestHeader("Authorization") String authHeader) throws Exception {

		log.info("Password change request received for authenticated user.");

		if (!PasswordValidatorUtil.isValid(changePasswordRequest.getNewPassword())) {
			log.warn("Password change failed: Weak password attempt.");
			return ResponseUtil.buildResponse(new ApiResponseDTO<>(false,
							"New password must be at least 8 characters long and include uppercase, lowercase, a number, and a special character."),
					HttpStatus.BAD_REQUEST);
		}

		userService.changePassword(changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword(),
				authHeader);

		log.info("Password changed successfully.");
		return ResponseUtil.buildResponse(new ApiResponseDTO<>(true, "Password changed successfully"), HttpStatus.OK);
	}

	@GetMapping("/${api.user.check-username-exists}")
	public ResponseEntity<ApiResponseDTO<Map<String, Object>>> checkUsernameExists(
			@RequestParam("username") String username) {

		log.info("Checking existence of username: {}", username);

		boolean exists = userService.usernameExists(username);

		Map<String, Object> data = Map.of("username", username, "exists", exists);

		return ResponseUtil.buildResponse(
				new ApiResponseDTO<>(data, exists ? "Username already taken" : "Username available"), HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CreateUserResponse>> changeUserInfoRequest(@PathVariable Long id,
			@Valid @RequestBody UpdateUserRequest request, BindingResult bindingResult) {
		return ResponseEntity.ok(new ApiResponseDTO<>(userService.updateUser(request, id, bindingResult)));

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deactivateUserRequest(@PathVariable Long id){
			userService.deactivateUser(id);
			return ResponseEntity.noContent().build();
	}
}
