package com.project.edu_service.mappers;

import com.project.edu_service.Dtos.LessonDto;
import com.project.edu_service.entityes.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toEntity(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setVideoLink(lessonDto.getVideoLink());
        return lesson;
    }
}
