package org.oneProjectOneMonth.lms.feature.admin.api;

import java.util.List;
import java.util.stream.Collectors;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.admin.domain.dto.UpdateUserRequest;
import org.oneProjectOneMonth.lms.feature.admin.domain.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/${api.base.path}/${api.user.base.path}")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	private final AdminService adminService;

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<?>> changeUserInfoRequest(@PathVariable Long id,
			@Valid @RequestBody UpdateUserRequest updatedUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ApiResponseDTO<>("error", errors.toString()));
		}
		try {
            if (updatedUser.getName() != null && updatedUser.getName().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", "Name cannot be empty."));
            }
            if (updatedUser.getEmail() != null && updatedUser.getEmail().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", "Email cannot be empty."));
            }
            if (updatedUser.getAddress() != null && updatedUser.getAddress().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", "Address cannot be empty."));
            }
            if (updatedUser.getPhone() != null && updatedUser.getPhone().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", "Phone number cannot be empty."));
            }
            if (updatedUser.getProfilePhoto() != null && updatedUser.getProfilePhoto().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", "Profile Photo cannot be empty."));
            }

            if (updatedUser.getNrc() != null && updatedUser.getNrc().isBlank()) {
                return ResponseEntity.badRequest().body(new ApiResponseDTO<>("error", "NRC cannot be empty."));
            }
            if (updatedUser.getEduBackground() != null && updatedUser.getEduBackground().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", "Education Background cannot be empty."));
            }

            updatedUser = adminService.updateUser(updatedUser, id);

            return ResponseEntity.ok(new ApiResponseDTO<>(updatedUser, "User update successful."));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>("error", e.getMessage()));
        }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<?>> deactivateUserRequest(@PathVariable Long id){
		try {
			adminService.deactivateUser(id);
			return ResponseEntity.ok(new ApiResponseDTO<>("User deactivation success."));
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>("error", e.getMessage()));
		}
	}
}
