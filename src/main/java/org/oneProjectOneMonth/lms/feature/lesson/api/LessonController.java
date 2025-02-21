package org.oneProjectOneMonth.lms.feature.lesson.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.LessonResponseDto;
import org.oneProjectOneMonth.lms.feature.lesson.domain.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.base.path}/${api.lesson.base.path}")
@RequiredArgsConstructor
@Slf4j
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<LessonResponseDto>> createLesson(@RequestBody CreateLessonRequest createLessonRequest) throws Exception {
        log.info("Creating new lesson: {}", createLessonRequest.getTitle());
        LessonResponseDto response = lessonService.createLesson(createLessonRequest);
        return ResponseEntity.ok(new ApiResponseDTO<>(response, "Lesson created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<LessonResponseDto>>> getAllLessons() throws Exception {
        log.info("Fetching all lessons");
        List<LessonResponseDto> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(new ApiResponseDTO<>(lessons, "All lessons fetched successfully"));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<ApiResponseDTO<LessonResponseDto>> getLessonById(@PathVariable Long lessonId) throws Exception {
        log.info("Fetching lesson with ID: {}", lessonId);
        LessonResponseDto lesson = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok(new ApiResponseDTO<>(lesson, "Lesson fetched successfully"));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<ApiResponseDTO<LessonResponseDto>> updateLesson(@PathVariable Long lessonId, @RequestBody CreateLessonRequest updateRequest) throws Exception {
        log.info("Updating lesson with ID: {}", lessonId);
        LessonResponseDto updatedLesson = lessonService.updateLesson(lessonId, updateRequest);
        return ResponseEntity.ok(new ApiResponseDTO<>(updatedLesson, "Lesson updated successfully"));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteLesson(@PathVariable Long lessonId) throws Exception {
        log.info("Deleting lesson with ID: {}", lessonId);
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("Lesson deleted successfully"));
    }
}
