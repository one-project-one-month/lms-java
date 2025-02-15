package org.oneProjectOneMonth.lms.feature.enrollment.domain.repository;

import org.oneProjectOneMonth.lms.feature.enrollment.domain.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
