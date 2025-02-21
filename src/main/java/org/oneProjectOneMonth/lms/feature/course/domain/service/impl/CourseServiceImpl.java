package org.oneProjectOneMonth.lms.feature.course.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.exception.EntityNotFoundException;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.repository.CourseRepository;
import org.oneProjectOneMonth.lms.feature.course.domain.service.CourseService;
import org.oneProjectOneMonth.lms.feature.course.domain.util.CourseUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
    public ApiResponseDTO<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .map(course -> new ApiResponseDTO<>(course, "Course found successfully"))
                .orElseGet(() -> new ApiResponseDTO<>("NOT_FOUND", "Course with ID " + courseId + " not found"));
    }

    @Override
    public ApiResponseDTO<Course> addCourse(Course course) {
        return new ApiResponseDTO<>(courseRepository.save(course), "Course added successfully");
    }

    @Override
    public ApiResponseDTO<Course> updateCourse(Long courseId, Course course) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found"));

        modelMapper.map(course, existingCourse);
        Course updatedCourse = courseRepository.save(existingCourse);
        return new ApiResponseDTO<>(updatedCourse, "Course Updated successfully");
    }

    @Override
    public ApiResponseDTO<Boolean> deleteCourseByCourseId(Long courseId) {
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
            return new ApiResponseDTO<>(true , "Course Deleted Successfully");
        } else {
            return new ApiResponseDTO<>("The Id was not found", "Course with ID " + courseId + " not found");
        }
    }
}
