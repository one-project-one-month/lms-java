package org.oneProjectOneMonth.lms.feature.enrollment.domain.dto;

import lombok.Data;

@Data
public class EnrollmentRequestDto {
    private Long studentId;
    private Long courseId;
}
