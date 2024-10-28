package com.project.edu_service.repository;

import com.project.edu_service.entityes.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositry extends JpaRepository <Comment, Long> {
}
