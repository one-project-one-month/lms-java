package org.oneProjectOneMonth.lms.feature.lesson.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String videoUrl;
    private String lessonDetail;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long courseId; 
}
