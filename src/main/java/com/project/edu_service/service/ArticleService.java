package com.project.edu_service.service;

import com.project.edu_service.Dtos.ArticleDtoResp;
import com.project.edu_service.Dtos.CommentDtoResp;
import com.project.edu_service.entityes.Article;
import com.project.edu_service.entityes.Comment;
import com.project.edu_service.entityes.Users;
import com.project.edu_service.repository.ArticleRepository;
import com.project.edu_service.repository.CommentRepositry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final CommentRepositry commentRepositry;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public void save (Article article) {
        logger.info("into article save method");
        article.setUsers(userService.getCurrentUser());
        logger.info("set users");
        article.setCreatedAt(LocalDateTime.now());
        articleRepository.save(article);
    }

    @Transactional
    public ArticleDtoResp getByTitle (String title) {
        Article article = articleRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Статья не найдена"));

        Long commentCount = article.getComments() != null ? (long) article.getComments().size() : 0;
        String authorName = article.getUsers().getUsername();

        return new ArticleDtoResp(article.getContent(), article.getTitle(), commentCount, authorName);
    }

    @Transactional
    public void addComment(String title, Comment comment) {
        Article article = articleRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Статья не найдена"));

        comment.setArticle(article);
        comment.setUsers(userService.getCurrentUser());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepositry.save(comment);
    }

    @Transactional
    public void addReply(Long parentId, Comment reply) {
        Comment parantComment = commentRepositry.findById(parentId)
                .orElseThrow(() -> new RuntimeException("parent не найден"));

        reply.setArticle(parantComment.getArticle());
        reply.setUsers(userService.getCurrentUser());
        reply.setCreatedAt(LocalDateTime.now());
        reply.setParent(parantComment);

        commentRepositry.save(reply);
    }

    @Transactional
    public List<CommentDtoResp> getAllCommentsByArticleTitle(String title) {
        Article article = articleRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Статья не найдена"));

        List<Comment> comments = article.getComments();
        List<CommentDtoResp> commentDtoResps = new ArrayList<>();

        for (Comment comment : comments) {
            String parentUsername = comment.getParent() != null ? comment.getParent().getUsers().getUsername() : null;

            CommentDtoResp commentDto = new CommentDtoResp(
                    comment.getUsers().getUsername(),
                    article.getTitle(),
                    parentUsername,
                    comment.getContent()
            );
            commentDtoResps.add(commentDto);
        }
        return commentDtoResps;
    }

    @Transactional
    public void deleteByTitle(String title) {
        articleRepository.deleteByTitle(title);
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }
}
