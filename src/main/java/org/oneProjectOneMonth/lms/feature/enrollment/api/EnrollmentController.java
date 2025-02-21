package org.oneProjectOneMonth.lms.feature.enrollment.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.dto.EnrollmentRequestDto;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.dto.EnrollmentResponseDto;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.base.path}/${api.enrollment.base.path}")
@Tag(name = "Enrollment", description = "Enrollment API")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(@RequestBody EnrollmentRequestDto request) {
        log.info("Enrolling student {} to course {}", request.getStudentId(), request.getCourseId());
        EnrollmentResponseDto response = enrollmentService.enrollStudent(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> getEnrollment(@PathVariable Long id) {
        log.info("Fetching enrollment with ID {}", id);
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {
        log.info("Fetching all enrollments");
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }
}
