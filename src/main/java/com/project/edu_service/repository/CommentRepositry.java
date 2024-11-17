package com.project.edu_service.repository;

import com.project.edu_service.entityes.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepositry extends JpaRepository <Comment, Long> {

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.article.id = :articleId")
    long countByArticleId(UUID articleId);

    @Query("SELECT c FROM Comment c WHERE c.article.id = (SELECT a.id FROM Article a WHERE a.title = :title)")
    List<Comment> findAllByArticleTitle(String title);
}
