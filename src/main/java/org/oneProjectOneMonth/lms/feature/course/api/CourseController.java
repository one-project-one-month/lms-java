package org.oneProjectOneMonth.lms.feature.course.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author : Min Myat Thu Kha
 * Created At : 20/02/2025, Feb
 * Project Name : lms-java
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("courses")
@Tag(name = "Course", description = "Course API")
@Slf4j
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Course>>> getCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Course>> getCourseById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

//    @GetMapping("/{courseId}")
//    public ResponseEntity<ApiResponseDTO<Course>> getCourseById(@PathVariable Long courseId) {
//        return ResponseEntity.ok(courseService.getCourseById(courseId));
//    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Course>> addCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ApiResponseDTO<Course>> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponseDTO<Boolean>> deleteCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.deleteCourseByCourseId(courseId));
    }
}
