package org.oneProjectOneMonth.lms.feature.instructor.domain.repository;

import java.util.Optional;

import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByNrc(String nrc);

    Optional<Instructor> findByUser(User user);

	@Query("SELECT i FROM Instructor i WHERE i.user.id = :userId")
	Optional<Instructor> findInstructorByUserId(Long userId);
}

