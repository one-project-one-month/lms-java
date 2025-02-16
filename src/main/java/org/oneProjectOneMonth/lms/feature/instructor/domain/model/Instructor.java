package org.oneProjectOneMonth.lms.feature.instructor.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;

import java.time.LocalDateTime;

@Data
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nrc;

    @Column(nullable = false, length = 500)
    private String eduBackground;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
