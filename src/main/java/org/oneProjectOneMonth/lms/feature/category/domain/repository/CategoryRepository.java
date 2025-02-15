package org.oneProjectOneMonth.lms.feature.category.domain.repository;

import org.oneProjectOneMonth.lms.feature.category.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
