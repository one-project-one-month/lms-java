package org.oneProjectOneMonth.lms.feature.lesson.api;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;
import org.oneProjectOneMonth.lms.feature.lesson.domain.service.LessonService;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponse;
import org.oneProjectOneMonth.lms.config.response.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/${api.base.path}/${api.lesson.base.path}")
@RequiredArgsConstructor
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<ApiResponse> createLesson(
            @Validated @RequestBody CreateLessonRequest createLessonRequest,
            HttpServletRequest request
    ) throws Exception {
        log.info("Creating new lesson: {}", createLessonRequest.getTitle());

        Object createdLesson = lessonService.createLesson(createLessonRequest);

        log.info("Lesson created successfully: {}", createLessonRequest.getTitle());

        ApiResponse successResponse = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.CREATED.value())
                .data(createdLesson)
                .message("Lesson created successfully")
                .build();

        return ResponseUtil.buildResponse(request, successResponse, 0L);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllLessons(HttpServletRequest request) throws Exception {
        log.info("Retrieving all lessons");
        Object lessons = lessonService.getAllLessons();

        ApiResponse successResponse = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(lessons != null ? lessons : Collections.emptyList())
                .message("Lessons retrieved successfully")
                .build();

        return ResponseUtil.buildResponse(request, successResponse, 0L);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<ApiResponse> getLessonById(
            @PathVariable Long lessonId, HttpServletRequest request) throws Exception {
        log.info("Retrieving lesson with ID: {}", lessonId);
        Object lesson = lessonService.getLessonById(lessonId);

        ApiResponse successResponse = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(lesson)
                .message("Lesson retrieved successfully")
                .build();

        return ResponseUtil.buildResponse(request, successResponse, 0L);
    }

    
    @PutMapping("/{lessonId}")
    public ResponseEntity<ApiResponse> updateLesson(
            @PathVariable Long lessonId,
            @Valid @RequestBody CreateLessonRequest updateRequest,
            HttpServletRequest request) throws Exception {
        log.info("Updating lesson with ID: {}", lessonId);
        Object updatedLesson = lessonService.updateLesson(lessonId, updateRequest);

        ApiResponse successResponse = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(updatedLesson)
                .message("Lesson updated successfully")
                .build();

        return ResponseUtil.buildResponse(request, successResponse, 0L);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<ApiResponse> deleteLesson(@PathVariable Long lessonId, HttpServletRequest request) throws Exception {
        log.info("Deleting lesson with ID: {}", lessonId);
        lessonService.deleteLesson(lessonId);

        ApiResponse successResponse = ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .message("Lesson deleted successfully")
                .build();

        return ResponseUtil.buildResponse(request, successResponse, 0L);
    }
}
