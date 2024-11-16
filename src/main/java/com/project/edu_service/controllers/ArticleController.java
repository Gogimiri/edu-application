package com.project.edu_service.controllers;

import com.project.edu_service.Dtos.ArticleDto;
import com.project.edu_service.Dtos.ArticleDtoResp;
import com.project.edu_service.Dtos.CommentDto;
import com.project.edu_service.Dtos.CommentDtoResp;
import com.project.edu_service.entityes.Article;
import com.project.edu_service.mappers.ArticleMapper;
import com.project.edu_service.mappers.CommentMapper;
import com.project.edu_service.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;


    @PostMapping("/add")
    public ResponseEntity<String> saveArticle(@RequestBody ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);


        articleService.save(article);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**ПОЛУЧЕНИЕ ВСЕХ СТАТЕЙ**/
    @GetMapping("/{title}")
    public ArticleDtoResp getArticelByTitle(@PathVariable String title) {
        return articleService.getByTitle(title);
    }

    /**ПОЛУЧИТЬ ВСЕ КОММЕНТАРИИ ПОД СТАТЬЕЙ**/
    @GetMapping("/comments/{title}")
    public List<CommentDtoResp> getAllCommentsByArticleTitle(@PathVariable String title) {
        return articleService.getAllCommentsByArticleTitle(title);
    }

    /**ДОБАВЛЕНИЕ КОММЕНТАРИЯ**/
    @PostMapping("/comment/{title}")
    public ResponseEntity<String>setComment(@PathVariable String title, @RequestBody CommentDto commentDto) {
        articleService.addComment(title, commentMapper.toEntity(commentDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**ДОБАВЛЕНИЕ ОТВЕТА НА КОММЕНТАРИЙ**/
    @PostMapping("/comment/reply/{parentId}")
    public ResponseEntity<String>setReply(@PathVariable Long parentId, @RequestBody CommentDto commentDto) {
        articleService.addReply(parentId, commentMapper.toEntity(commentDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**УДАЛЕНИЕ СТАТЬИ ПО ЗАГОЛОВКУ**/
    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteArticleById(String title) {
        articleService.deleteByTitle(title);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
