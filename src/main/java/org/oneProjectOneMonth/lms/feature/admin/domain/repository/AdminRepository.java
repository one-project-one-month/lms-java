package org.oneProjectOneMonth.lms.feature.admin.domain.repository;

import org.oneProjectOneMonth.lms.feature.admin.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
