package org.oneProjectOneMonth.lms.feature.lesson.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.LessonResponseDto;
import org.oneProjectOneMonth.lms.feature.lesson.domain.service.LessonService;
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
    public ResponseEntity<LessonResponseDto> createLesson(@RequestBody CreateLessonRequest createLessonRequest) throws Exception {
        log.info("Creating new lesson: {}", createLessonRequest.getTitle());
        LessonResponseDto response = lessonService.createLesson(createLessonRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> getAllLessons() throws Exception {
        log.info("Fetching all lessons");
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonResponseDto> getLessonById(@PathVariable Long lessonId) throws Exception {
        log.info("Fetching lesson with ID: {}", lessonId);
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonResponseDto> updateLesson(@PathVariable Long lessonId, @RequestBody CreateLessonRequest updateRequest) throws Exception {
        log.info("Updating lesson with ID: {}", lessonId);
        LessonResponseDto response = lessonService.updateLesson(lessonId, updateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) throws Exception {
        log.info("Deleting lesson with ID: {}", lessonId);
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}
