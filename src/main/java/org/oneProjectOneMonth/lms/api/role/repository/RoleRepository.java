package org.oneProjectOneMonth.lms.api.role.repository;

import org.oneProjectOneMonth.lms.api.role.model.Role;
import org.oneProjectOneMonth.lms.api.role.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
