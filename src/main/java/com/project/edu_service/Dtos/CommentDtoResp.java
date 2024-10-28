package com.project.edu_service.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentDtoResp {

    private String username;

    private String articleTitle;

    private String parantUsername;

    private String content;
}
