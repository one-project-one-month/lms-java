package org.oneProjectOneMonth.lms.feature.category.domain.service;

import org.oneProjectOneMonth.lms.feature.category.domain.dto.CategoryRequest;
import org.oneProjectOneMonth.lms.feature.category.domain.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}