package org.oneProjectOneMonth.lms.feature.admin.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.oneProjectOneMonth.lms.feature.user.domain.model.User;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
}
