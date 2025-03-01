package org.oneProjectOneMonth.lms.feature.course.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oneProjectOneMonth.lms.feature.category.domain.model.Category;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author : Min Myat Thu Kha
 * Created At : 24/02/2025, Feb
 * Project Name : lms-java
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    @NotNull(message = "Instructor is required")
    private Instructor instructor;
    @NotNull(message = "Course name is required")
    private String courseName;
    @NotNull(message = "Course thumbnail is required")
    @Size(max = 255)
    private String thumbnail;

    private boolean isAvailable;

    @NotNull(message = "Course type is required")
    private String type;
    @NotNull(message = "Course name is required")
    private String level;
    @NotBlank(message = "Course Description is required")
    @Size(max = 500)
    private String description;

    private int duration; // in hours

    @NotNull(message = "Course Original Price is Required")
    private BigDecimal originalPrice;

    private BigDecimal currentPrice;

    @NotNull(message = "Course Category is required")
    private Category category;

    @NotEmpty
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
