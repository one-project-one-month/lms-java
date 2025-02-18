package org.oneProjectOneMonth.lms.feature.instructor.domain.repository;

import java.util.Optional;

import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	@Query("SELECT i FROM Instructor i WHERE i.user.id = :userId")
	Optional<Instructor> findInstructorByUserId(Long userId);
}

