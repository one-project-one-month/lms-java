package org.oneProjectOneMonth.lms.feature.category.domain.service;

import org.oneProjectOneMonth.lms.feature.category.domain.dto.CategoryResponse;
import org.oneProjectOneMonth.lms.feature.category.domain.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest CategoryRequest);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long categoryId);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest updateRequest);
    void deleteCategory(Long categoryId);
}
