package org.oneProjectOneMonth.lms.feature.student.domain.repository;

import org.oneProjectOneMonth.lms.feature.student.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
