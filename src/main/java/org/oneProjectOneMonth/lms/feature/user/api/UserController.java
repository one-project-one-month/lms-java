package org.oneProjectOneMonth.lms.feature.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.ChangePasswordRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.feature.user.domain.service.UserService;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.PasswordValidatorUtil;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponse;
import org.oneProjectOneMonth.lms.config.response.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Operation(summary = "Change Password Api")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
            HttpServletRequest request,
            @RequestHeader("Authorization") String authHeader) throws Exception {

        log.info("Password change request received for authenticated user.");

        if (!PasswordValidatorUtil.isValid(changePasswordRequest.getNewPassword())) {
            log.warn("Password change failed: Weak password attempt.");
            return ResponseUtil.buildResponse(request, ApiResponse.builder()
                    .success(0)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .data(false)
                    .message("New password must be at least 8 characters long and include uppercase, lowercase, a number, and a special character.")
                    .build(), 0L);
        }

        userService.changePassword(changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword(), authHeader);

        log.info("Password changed successfully.");

        ApiResponse successResponse = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(true)
                .message("Password changed successfully")
                .build();

        return ResponseUtil.buildResponse(request, successResponse, 0L);
    }

    @GetMapping("/${api.user.check-username-exists}")
    @Operation(summary = "Check Username Exists Api")
    public ResponseEntity<ApiResponse> checkUsernameExists(
            @RequestParam("username") String username, HttpServletRequest request) {

        log.info("Checking existence of username: {}", username);

        boolean exists = userService.usernameExists(username);

        ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(Map.of("username", username, "exists", exists))
                .message(exists ? "Username already taken" : "Username available")
                .build();

        return ResponseUtil.buildResponse(request, response, 0L);
    }
}
