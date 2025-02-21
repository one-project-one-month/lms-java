package org.oneProjectOneMonth.lms.feature.category.domain.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.oneProjectOneMonth.lms.feature.category.domain.dto.CategoryResponse;
import org.oneProjectOneMonth.lms.feature.category.domain.dto.CategoryRequest;
import org.oneProjectOneMonth.lms.feature.category.domain.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.base.path}/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> createCategory(@RequestBody CategoryRequest CategoryRequest) {
        log.info("Creating new category: {}", CategoryRequest.getName());
        CategoryResponse response = categoryService.createCategory(CategoryRequest);
        return ResponseEntity.ok(new ApiResponseDTO<>(response, "Category created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CategoryResponse>>> getAllCategories() {
        log.info("Fetching all categories");
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponseDTO<>(categories, "All categories fetched successfully"));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> getCategoryById(@PathVariable Long categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponseDTO<>(category, "Category fetched successfully"));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest updateRequest) {
        log.info("Updating category with ID: {}", categoryId);
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, updateRequest);
        return ResponseEntity.ok(new ApiResponseDTO<>(updatedCategory, "Category updated successfully"));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteCategory(@PathVariable Long categoryId) {
        log.info("Deleting category with ID: {}", categoryId);
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("Category deleted successfully"));
    }
}
