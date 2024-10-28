package com.project.edu_service.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDtoResp {

    private String content;

    private String title;

    private Long commentCount;

    private String authorName;
}
