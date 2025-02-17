package org.oneProjectOneMonth.lms.feature.user.domain.response;

import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record CreateUserResponse(
        Long id,
        String name,
        String username,
        String email,
        String phone,
        LocalDate dob,
        String address,
        String profilePhoto,
        boolean available,
        String roleName,
        Long userId,
        String nrc,
        String eduBackground,
        LocalDateTime  createdAt,
        LocalDateTime updatedAt
) {
    public static CreateUserResponse fromUser(User user, Instructor instructor) {
        String roleName = user.getRoles().stream()
                .findFirst()
                .map(role -> role.getName().name())
                .orElse(null);

        return new CreateUserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getDob(),
                user.getAddress(),
                user.getProfilePhoto(),
                user.isAvailable(),
                roleName,
                user.getId(),
                instructor != null ? instructor.getNrc() : null,
                instructor != null ? instructor.getEduBackground() : null,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}



