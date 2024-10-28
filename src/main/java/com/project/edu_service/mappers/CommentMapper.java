package com.project.edu_service.mappers;

import com.project.edu_service.Dtos.CommentDto;
import com.project.edu_service.entityes.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        return comment;
    }
}
