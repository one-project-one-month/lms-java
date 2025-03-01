package org.oneProjectOneMonth.lms.feature.course.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseRequest;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseResponse;
import org.oneProjectOneMonth.lms.feature.course.domain.dto.CreateCourseDto;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.service.CourseService;
import org.oneProjectOneMonth.lms.feature.student.domain.model.Student;
import org.springframework.data.repository.support.Repositories;
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
@RequestMapping("/${api.base.path}/${api.course.base.path}")
@Tag(name = "Course", description = "Course API")
public class CourseController {
    private final CourseService courseService;

//    Todo - Future Improvement should be Search With Pagination
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CourseResponse>>> getCourses(
//            From
//            To
//            Size
//            SearchByName
//            SearchByCreatedDate
//            SearchBy ....
    ) {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CourseResponse>> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

//    @GetMapping("/{courseId}")
//    public ResponseEntity<ApiResponseDTO<Course>> getCourseById(@PathVariable Long courseId) {
//        return ResponseEntity.ok(courseService.getCourseById(courseId));
//    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CourseResponse>> addCourse(
            @RequestBody CreateCourseDto course,
            @RequestParam("id") Long instructorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course, instructorId));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ApiResponseDTO<CourseResponse>> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest course) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponseDTO<Boolean>> deleteCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.deleteCourseByCourseId(courseId));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<ApiResponseDTO<CourseResponse>> ToggleAvailableCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.ToggleAvailableCourse(courseId));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponseDTO<List<CourseResponse>>> getPopularCourses() {
//        Todo - Later Improvement
        return ResponseEntity.ok(courseService.getPopularCourses());
    }

    @GetMapping("/trending")
    public ResponseEntity<ApiResponseDTO<List<CourseResponse>>> getTrendingCourses() {
        return ResponseEntity.ok(courseService.getTrendingCourses());
    }

    @PutMapping("/{id}/apply-discount/{currentPrice}")
    public ResponseEntity<ApiResponseDTO<CourseResponse>> applyDiscount(@PathVariable Long id, @PathVariable Double currentPrice) {
//        Todo - Later Improvement
        return ResponseEntity.ok(courseService.applyDiscount(id, currentPrice));
    }


}
