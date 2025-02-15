package org.oneProjectOneMonth.lms.feature.social.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false, unique = true)
    private Course course;

    private String facebook;
    private String x; // Formerly Twitter
    private String telegram;
    private String phone;
    private String email;
}
