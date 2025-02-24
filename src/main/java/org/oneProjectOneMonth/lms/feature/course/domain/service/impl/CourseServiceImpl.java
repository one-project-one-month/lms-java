package org.oneProjectOneMonth.lms.feature.course.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.exception.EntityNotFoundException;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.config.utils.EntityUtil;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseRequest;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseResponse;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CreateCourseDto;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.repository.CourseRepository;
import org.oneProjectOneMonth.lms.feature.course.domain.service.CourseService;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.instructor.domain.repository.InstructorRepository;
import org.oneProjectOneMonth.lms.feature.lesson.domain.model.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author : Min Myat Thu Kha
 * Created At : 20/02/2025, Feb
 * Project Name : lms-java
 **/
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
//    private final CourseUtility courseUtility;
    private final ModelMapper modelMapper;

// Former Piece of Shit
//    @Override
//    public ApiResponseDTO<List<Course>> getAllCourses(int page, int size) {
//        Pageable pageable = (Pageable) PageRequest.of(page, size);
//        Page<Course> coursePage = courseRepository.findAll((org.springframework.data.domain.Pageable) pageable);
//        return new ApiResponseDTO<>(coursePage.getContent(), "Courses retrieved successfully");
//    }


    @Override
    public ApiResponseDTO<List<Course>> getAllCourses(
            //From
            //To
            //Size
    ) {
       List<Course> courses =  courseRepository.findAll();
        return new ApiResponseDTO<>(courses, "List of courses");
    }

    @Override
    public ApiResponseDTO<CourseResponse> getCourseById(Long courseId) {
        Course course = EntityUtil.getEntityById(courseRepository, courseId);
        CourseResponse courseResponse = DtoUtil.map(course, CourseResponse.class, modelMapper);
        return new ApiResponseDTO<>(courseResponse , "The Course is Found with id: " + courseId);
    }

    @Override
    public ApiResponseDTO<CourseResponse> addCourse(CreateCourseDto course, Long instructorId) {
        Instructor instructor = EntityUtil.getEntityById(instructorRepository, instructorId);
        Course courseEntity = Course.builder()
                .instructor(instructor)
                .description(course.getDescription())
                .courseName(course.getCourseName())
                .level(course.getLevel())
                .category(course.getCategory())
                .type(course.getType())
                .duration(course.getDuration())
                .originalPrice(course.getOriginalPrice())
                .currentPrice(course.getCurrentPrice())
                .thumbnail(course.getThumbnail())
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        Course savedCourse = courseRepository.save(courseEntity);

        CourseResponse courseResponse = DtoUtil.map(savedCourse, CourseResponse.class, modelMapper);
        courseResponse.setInstructorId(savedCourse.getInstructor().getId());
        courseResponse.setCategory(savedCourse.getCategory().getName());

        return new ApiResponseDTO<>(courseResponse , "Course added successfully");
    }

    @Transactional
    @Override
    public ApiResponseDTO<CourseResponse> updateCourse(Long courseId, CourseRequest courseRequest) {
        Course existingCourse = EntityUtil.getEntityById(courseRepository, courseId);
        existingCourse.setDescription(courseRequest.getDescription());
        existingCourse.setCourseName(courseRequest.getCourseName());
        existingCourse.setLevel(courseRequest.getLevel());
        existingCourse.setCategory(courseRequest.getCategory());
        existingCourse.setType(courseRequest.getType());
        existingCourse.setDuration(courseRequest.getDuration());
        existingCourse.setOriginalPrice(courseRequest.getOriginalPrice());
        existingCourse.setCurrentPrice(courseRequest.getCurrentPrice());
        existingCourse.setThumbnail(courseRequest.getThumbnail());
        existingCourse.setAvailable(courseRequest.isAvailable());
        existingCourse.setUpdatedAt(LocalDateTime.now());
        Course updatedCourse = courseRepository.save(existingCourse);

        CourseResponse courseResponse = DtoUtil.map(updatedCourse, CourseResponse.class, modelMapper);
        return new ApiResponseDTO<>(courseResponse, "Course Updated successfully");
    }

    @Transactional
    @Override
    public ApiResponseDTO<Boolean> deleteCourseByCourseId(Long courseId) {
        Course course = EntityUtil.getEntityById(courseRepository, courseId);
        courseRepository.delete(course);
        return new ApiResponseDTO<>(true, "Course Deleted Successfully");
    }

    @Transactional
    @Override
    public ApiResponseDTO<CourseResponse> ToggleAvailableCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found"));
        CourseResponse courseResponse = DtoUtil.map(course, CourseResponse.class, modelMapper);
        return new ApiResponseDTO<>(courseResponse, "Course " + courseId + "'s availability is now changed");
    }
}
