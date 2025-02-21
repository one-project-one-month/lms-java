package org.oneProjectOneMonth.lms.feature.lesson.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.oneProjectOneMonth.lms.config.utils.DtoUtil;
import org.oneProjectOneMonth.lms.config.utils.EntityUtil;
import org.oneProjectOneMonth.lms.feature.course.domain.model.Course;
import org.oneProjectOneMonth.lms.feature.course.domain.repository.CourseRepository;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.CreateLessonRequest;
import org.oneProjectOneMonth.lms.feature.lesson.domain.dto.LessonResponseDto;
import org.oneProjectOneMonth.lms.feature.lesson.domain.model.Lesson;
import org.oneProjectOneMonth.lms.feature.lesson.domain.repository.LessonRepository;
import org.oneProjectOneMonth.lms.feature.lesson.domain.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<LessonResponseDto> getAllLessons() throws Exception {
        log.info("Fetching all lessons");
        return DtoUtil.mapList(lessonRepository.findAll(), LessonResponseDto.class, modelMapper);
    }

    @Override
    public LessonResponseDto getLessonById(Long lessonId) throws Exception {
        log.info("Fetching lesson with ID: {}", lessonId);
        Lesson lesson = EntityUtil.getEntityById(lessonRepository, lessonId);
        return DtoUtil.map(lesson, LessonResponseDto.class, modelMapper);
    }

    @Transactional
    @Override
    public LessonResponseDto createLesson(CreateLessonRequest createLessonRequest) throws Exception {
        log.info("Creating a new lesson: {}", createLessonRequest.getTitle());
        Course course = EntityUtil.getEntityById(courseRepository, createLessonRequest.getCourseId());
        Lesson lesson = Lesson.builder()
                .title(createLessonRequest.getTitle())
                .lessonDetail(createLessonRequest.getLessonDetail())
                .videoUrl(createLessonRequest.getVideoUrl())
                .available(createLessonRequest.isAvailable())
                .course(course)
                .build();
        Lesson savedLesson = lessonRepository.save(lesson);
        return DtoUtil.map(savedLesson, LessonResponseDto.class, modelMapper);
    }

    @Transactional
    @Override
    public LessonResponseDto updateLesson(Long lessonId, CreateLessonRequest updateRequest) throws Exception {
        log.info("Updating lesson with ID: {}", lessonId);
        Lesson lesson = EntityUtil.getEntityById(lessonRepository, lessonId);
        Course course = EntityUtil.getEntityById(courseRepository, updateRequest.getCourseId());
        lesson.setTitle(updateRequest.getTitle());
        lesson.setLessonDetail(updateRequest.getLessonDetail());
        lesson.setVideoUrl(updateRequest.getVideoUrl());
        lesson.setAvailable(updateRequest.isAvailable());
        lesson.setCourse(course);
        Lesson updatedLesson = lessonRepository.save(lesson);
        return DtoUtil.map(updatedLesson, LessonResponseDto.class, modelMapper);
    }

    @Transactional
    @Override
    public void deleteLesson(Long lessonId) throws Exception {
        log.info("Deleting lesson with ID: {}", lessonId);
        Lesson lesson = EntityUtil.getEntityById(lessonRepository, lessonId);
        lessonRepository.delete(lesson);
    }
}


