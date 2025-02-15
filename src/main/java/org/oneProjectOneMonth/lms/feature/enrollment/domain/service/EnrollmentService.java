package org.oneProjectOneMonth.lms.feature.enrollment.domain.service;

import org.oneProjectOneMonth.lms.feature.enrollment.domain.dto.EnrollmentRequestDto;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.dto.EnrollmentResponseDto;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDto enrollStudent(EnrollmentRequestDto request);
    EnrollmentResponseDto getEnrollmentById(Long id);
    List<EnrollmentResponseDto> getAllEnrollments();
}
