package org.oneProjectOneMonth.lms.feature.lesson.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.exception.EntityNotFoundException;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.config.utils.EntityUtil;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.repository.CourseRepository;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.LessonDto;
import org.oneProjectOneMonth.lms.feature.lesson.domain.model.Lesson;
import org.oneProjectOneMonth.lms.feature.lesson.domain.repository.LessonRepository;
import org.oneProjectOneMonth.lms.feature.lesson.domain.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public Object getAllLessons() throws Exception {
        try {
            log.info("Fetching all lessons from the database.");

            List<Lesson> lessons = lessonRepository.findAll();
            List<LessonDto> lessonDtoList = lessons.stream()
                    .map(lesson -> modelMapper.map(lesson, LessonDto.class))
                    .collect(Collectors.toList());

            log.info("Retrieved {} lessons.", lessons.size());
            return lessonDtoList;
        } catch (Exception e) {
            log.error("Error retrieving lessons: {}", e.getMessage());
            throw new Exception("Error retrieving lessons: " + e.getMessage());
        }
    }

    @Override
    public Object getLessonById(Long lessonId) throws Exception {
        try {
            log.info("Fetching lesson with ID: {}", lessonId);

            Lesson lesson = EntityUtil.getEntityById(lessonRepository, lessonId);
            return modelMapper.map(lesson, LessonDto.class);
        } catch (EntityNotFoundException e) {
            log.warn("Lesson not found with ID: {}", lessonId);
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving lesson by ID: {}", e.getMessage());
            throw new Exception("Error retrieving lesson: " + e.getMessage());
        }
    }

    

    @Override
    public Object createLesson(CreateLessonRequest createLessonRequest) throws Exception {
        try {
            log.info("Creating new lesson with title: {}", createLessonRequest.getTitle());

            Course course = courseRepository.findById(createLessonRequest.getCourseId())
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + createLessonRequest.getCourseId()));

            Lesson lesson = modelMapper.map(createLessonRequest, Lesson.class);
            lesson.setCourse(course);

            Lesson savedLesson = lessonRepository.save(lesson);
            log.info("Lesson created successfully with ID: {}", savedLesson.getId());

            return modelMapper.map(savedLesson, LessonDto.class);
        } catch (EntityNotFoundException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating lesson: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Object updateLesson(Long lessonId, CreateLessonRequest updateRequest) throws Exception {
        try {
            log.info("Updating lesson with ID: {}", lessonId);
            Course course = courseRepository.findById(updateRequest.getCourseId())
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + createLessonRequest.getCourseId()));


            Lesson lesson = EntityUtil.getEntityById(lessonRepository, lessonId);
            lesson.setCourse(course);
            lesson.setTitle(updateRequest.getTitle());
            lesson.setVideoUrl(updateRequest.getVideoUrl());
            lesson.setLessonDetail(updateRequest.getLessonDetail());
            lesson.setAvailable(updateRequest.isAvailable());

            Lesson updatedLesson = lessonRepository.save(lesson);
            log.info("Lesson updated successfully with ID: {}", updatedLesson.getId());

            return modelMapper.map(updatedLesson, LessonDto.class);
        } catch (EntityNotFoundException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating lesson: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteLesson(Long lessonId) throws Exception {
        try {
            log.info("Deleting lesson with ID: {}", lessonId);

            Lesson lesson = EntityUtil.getEntityById(lessonRepository, lessonId);
            lessonRepository.delete(lesson);

            log.info("Lesson deleted successfully with ID: {}", lessonId);
        } catch (EntityNotFoundException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error deleting lesson: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}

