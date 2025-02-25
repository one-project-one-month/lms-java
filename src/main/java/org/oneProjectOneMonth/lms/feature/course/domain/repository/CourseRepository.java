package org.oneProjectOneMonth.lms.feature.course.domain.repository;

import org.oneProjectOneMonth.lms.feature.course.domain.dto.CourseResponse;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c " +
            "LEFT JOIN Enrollment e ON c.id = e.course.id " +
            "GROUP BY c " +
            "ORDER BY COUNT(e.id) DESC")
    List<Course> findPopularCourses(Pageable pageable);

    @Query("SELECT c FROM Course c " +
            "LEFT JOIN Enrollment e ON c.id = e.course.id " +
            "WHERE e.enrollmentDate >= :lastWeek " +
            "GROUP BY c " +
            "ORDER BY COUNT(e.id) DESC")
    List<Course> findTrendingCourses(@Param("lastWeek") LocalDateTime lastWeek, Pageable pageable);
}
