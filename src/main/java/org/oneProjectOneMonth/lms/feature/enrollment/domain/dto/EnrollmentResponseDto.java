package org.oneProjectOneMonth.lms.feature.enrollment.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnrollmentResponseDto {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDateTime enrollmentDate;
}
