package org.oneProjectOneMonth.lms.feature.course.domain.repository;

import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
