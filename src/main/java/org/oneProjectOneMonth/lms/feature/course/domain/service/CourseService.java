package org.oneProjectOneMonth.lms.feature.course.domain.service;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;

import java.util.List;

/**
 * Author : Min Myat Thu Kha
 * Created At : 20/02/2025, Feb
 * Project Name : lms-java
 **/
public interface CourseService {

    ApiResponseDTO<List<Course>> getAllCourses();
    ApiResponseDTO<Course> getCourseById(Long courseId);
    ApiResponseDTO<Course> addCourse(Course course);
    ApiResponseDTO<Course> updateCourse(Long courseId, Course course);
    ApiResponseDTO<Boolean> deleteCourseByCourseId(Long courseId);
}
