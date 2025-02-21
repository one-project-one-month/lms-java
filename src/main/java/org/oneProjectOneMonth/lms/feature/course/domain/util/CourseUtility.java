package org.oneProjectOneMonth.lms.feature.course.domain.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.repository.CourseRepository;
import org.oneProjectOneMonth.lms.feature.course.domain.service.CourseService;
import org.springframework.stereotype.Component;

/**
 * Author : Min Myat Thu Kha
 * Created At : 21/02/2025, Feb
 * Project Name : lms-java
 **/

@Component
@RequiredArgsConstructor
public class CourseUtility {
//

//    Todo - No Need This is for joking no use class

    //
    private final CourseRepository courseRepository;

    public Boolean DIdNaeCourseShiLar(Long courseId) {
        return courseRepository.existsById(courseId);
    }

    public Course CourseKoByIdNaeShar(Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }
}
