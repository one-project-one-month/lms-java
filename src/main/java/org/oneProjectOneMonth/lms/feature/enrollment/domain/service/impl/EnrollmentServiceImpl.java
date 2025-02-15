package org.oneProjectOneMonth.lms.feature.enrollment.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.config.utils.EntityUtil;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.repository.CourseRepository;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.model.Enrollment;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.repository.EnrollmentRepository;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.service.EnrollmentService;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.dto.EnrollmentRequestDto;
import org.oneProjectOneMonth.lms.feature.enrollment.domain.dto.EnrollmentResponseDto;
import org.oneProjectOneMonth.lms.feature.student.domain.model.Student;
import org.oneProjectOneMonth.lms.feature.student.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public EnrollmentResponseDto enrollStudent(EnrollmentRequestDto request) {
        log.info("Processing enrollment for student {} in course {}", request.getStudentId(), request.getCourseId());

        Student student = EntityUtil.getEntityById(studentRepository, request.getStudentId());

        Course course = EntityUtil.getEntityById(courseRepository, request.getCourseId());

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollmentDate(LocalDateTime.now())
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return DtoUtil.map(savedEnrollment, EnrollmentResponseDto.class, modelMapper);
    }

    @Override
    public EnrollmentResponseDto getEnrollmentById(Long id) {
        Enrollment enrollment = EntityUtil.getEntityById(enrollmentRepository, id);
        return DtoUtil.map(enrollment, EnrollmentResponseDto.class, modelMapper);
    }

    @Override
    public List<EnrollmentResponseDto> getAllEnrollments() {
        return DtoUtil.mapList(enrollmentRepository.findAll(), EnrollmentResponseDto.class, modelMapper);
    }
}
