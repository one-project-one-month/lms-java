package org.oneProjectOneMonth.lms.feature.lesson.domain.service;
import java.util.List;

import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.LessonResponseDto;



public interface LessonService {
    List<LessonResponseDto> getAllLessons() throws Exception;

    LessonResponseDto getLessonById(Long lessonId) throws Exception;

    LessonResponseDto createLesson(CreateLessonRequest createLessonRequest) throws Exception;

    LessonResponseDto updateLesson(Long lessonId, CreateLessonRequest updateRequest) throws Exception;

    void deleteLesson(Long lessonId) throws Exception;
}
