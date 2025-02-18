package org.oneProjectOneMonth.lms.feature.lesson.domain.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for creating a new lesson.
 */
@Data
public class CreateLessonRequest {

    @NotNull(message = "Course ID is required.")
    private Long courseId;

    @NotBlank(message = "Title is required.")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters.")
    private String title;

    @Size(max = 255, message = "Video URL must not exceed 255 characters.")
    private String videoUrl;

    @Size(max = 500, message = "Lesson detail must not exceed 500 characters.")
    private String lessonDetail;

    private boolean available;
}
