package org.oneProjectOneMonth.lms.feature.student.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {
    @Column(nullable = false)
    private String name;
}
