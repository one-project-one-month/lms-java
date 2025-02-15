package org.oneProjectOneMonth.lms.feature.lesson.domain.repository;

import org.oneProjectOneMonth.lms.feature.lesson.domain.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
