package org.oneProjectOneMonth.lms.feature.instructor.domain.repository;

import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean existsByNrc(String nrc);

    Optional<Instructor> findByUser(User user);
}
