package org.oneProjectOneMonth.lms.feature.instructor.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "user_id")
public class Instructor extends User {
    @Column(nullable = false, unique = true)
    private String nrc;

    @Column(nullable = false, length = 500)
    private String eduBackground;
}
