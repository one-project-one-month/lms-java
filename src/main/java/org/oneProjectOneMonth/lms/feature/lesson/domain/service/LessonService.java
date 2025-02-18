package org.oneProjectOneMonth.lms.feature.lesson.domain.service;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;



public interface LessonService {
    Object getAllLessons() throws Exception;

    Object getLessonById(Long lessonId) throws Exception;

    Object createLesson(CreateLessonRequest createLessonRequest) throws Exception;

    Object updateLesson(Long lessonId, CreateLessonRequest updateRequest) throws Exception;

    void deleteLesson(Long lessonId) throws Exception;
}
