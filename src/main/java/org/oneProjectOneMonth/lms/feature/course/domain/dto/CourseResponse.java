package org.oneProjectOneMonth.lms.feature.course.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oneProjectOneMonth.lms.feature.category.domain.model.Category;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author : Min Myat Thu Kha
 * Created At : 22/02/2025, Feb
 * Project Name : lms-java
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private Long instructorId;
    private String courseName;
    private String thumbnail;
    private boolean isAvailable;
    private String type;
    private String level;
    private String description;
    private int duration;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
