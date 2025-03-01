package org.oneProjectOneMonth.lms.feature.course.domain.service;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseRequest;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseResponse;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CreateCourseDto;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;

import java.util.List;

/**
 * Author : Min Myat Thu Kha
 * Created At : 20/02/2025, Feb
 * Project Name : lms-java
 **/
public interface CourseService {

    ApiResponseDTO<List<CourseResponse>> getAllCourses();
    ApiResponseDTO<CourseResponse> getCourseById(Long courseId);
    ApiResponseDTO<CourseResponse> addCourse(CreateCourseDto course, Long instructorId);
    ApiResponseDTO<CourseResponse> updateCourse(Long courseId, CourseRequest course);
    ApiResponseDTO<Boolean> deleteCourseByCourseId(Long courseId);
    ApiResponseDTO<CourseResponse> ToggleAvailableCourse(Long courseId);
    ApiResponseDTO<List<CourseResponse>> getTrendingCourses();
    ApiResponseDTO<List<CourseResponse>> getPopularCourses();
    ApiResponseDTO<CourseResponse> applyDiscount(Long courseId, Double currentPrice);
}
