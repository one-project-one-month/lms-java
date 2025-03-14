package org.oneProjectOneMonth.lms.feature.instructor.domain.service;

import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : Min Myat Thu Kha
 * Created At : 15/02/2025, Feb
 * Project Name : lms-java
 **/
public interface InstructorService {

    List<Instructor> getAllInstructors();
    Instructor getInstructor(String instructorId);
    Instructor createInstructor(Instructor instructor);
    Instructor updateInstructor(Long id, Instructor instructor);
    void deleteInstructor(Long id);
}
