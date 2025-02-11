package org.oneProjectOneMonth.lms.feature.role.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private Long id;
    private String name;
}
