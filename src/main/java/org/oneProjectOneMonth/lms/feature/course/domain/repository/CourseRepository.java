package org.oneProjectOneMonth.lms.feature.course.domain.repository;

import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
