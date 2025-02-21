package org.oneProjectOneMonth.lms.feature.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.user.domain.dto.ChangePasswordRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.request.CreateUserRequest;
import org.oneProjectOneMonth.lms.feature.user.domain.response.CreateUserResponse;
import org.oneProjectOneMonth.lms.feature.user.domain.service.UserService;
import org.oneProjectOneMonth.lms.feature.user.domain.utils.PasswordValidatorUtil;
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
public class UserController {

    private final UserService userService;

    @PostMapping
     public ResponseEntity<ApiResponseDTO<CreateUserResponse>> signUp(
             @Valid @RequestBody CreateUserRequest request
    ){
        return ResponseEntity.ok(new ApiResponseDTO<>(userService.signUp(request)));
    }

    @GetMapping
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
            return ResponseUtil.buildResponse(
                    new ApiResponseDTO<>(false, "New password must be at least 8 characters long and include uppercase, lowercase, a number, and a special character."),
                    HttpStatus.BAD_REQUEST
            );
        }

        userService.changePassword(changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword(), authHeader);

        log.info("Password changed successfully.");
        return ResponseUtil.buildResponse(
                new ApiResponseDTO<>(true, "Password changed successfully"),
                HttpStatus.OK
        );
    }

    @GetMapping("/${api.user.check-username-exists}")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> checkUsernameExists(
            @RequestParam("username") String username) {

        log.info("Checking existence of username: {}", username);

        boolean exists = userService.usernameExists(username);

        Map<String, Object> data = Map.of("username", username, "exists", exists);

        return ResponseUtil.buildResponse(
                new ApiResponseDTO<>(data, exists ? "Username already taken" : "Username available"),
                HttpStatus.OK
        );
    }
}
