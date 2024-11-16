package com.project.edu_service.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDtoResp {

    private String content;

    private String title;

    private Optional<Long> commentCount;

    private String authorName;
}
