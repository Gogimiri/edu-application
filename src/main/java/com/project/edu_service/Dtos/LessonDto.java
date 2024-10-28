package com.project.edu_service.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LessonDto {

    private String title;

    private String description;

    private String videoLink;
}
