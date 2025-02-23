package org.oneProjectOneMonth.lms.feature.instructor.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.oneProjectOneMonth.lms.config.exception.EntityNotFoundException;
import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
import org.oneProjectOneMonth.lms.feature.instructor.domain.repository.InstructorRepository;
import org.oneProjectOneMonth.lms.feature.instructor.domain.service.InstructorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : Min Myat Thu Kha
 * Created At : 15/02/2025, Feb
 * Project Name : lms-java
 **/
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor getInstructor(String instructorId) {
        return instructorRepository.findById(Long.parseLong(instructorId))
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found"));
    }

    @Transactional
    @Override
    public Instructor createInstructor(Instructor instructor) {
        if (instructor == null) {
            return null;
        }
        return instructorRepository.save(instructor);
    }

    @Transactional
    @Override
    public Instructor updateInstructor(Long id, Instructor instructor) {
        return instructorRepository.findById(id)
                .map(existingInstructor -> {
//                    existingInstructor.setName(instructor.getName());
//                    existingInstructor.setEmail(instructor.getEmail());
                    existingInstructor.setNrc(instructor.getNrc());
                    existingInstructor.setEduBackground(instructor.getEduBackground());
                    return instructorRepository.save(existingInstructor);
                })
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + id + " not found"));
    }

    @Transactional
    @Override
    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + id + " not found"));
        instructorRepository.delete(instructor);
    }
}

