package org.oneProjectOneMonth.lms.feature.instructor.domain.repository;

import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
