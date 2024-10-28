package com.project.edu_service.controllers;

import com.project.edu_service.Dtos.CommentDto;
import com.project.edu_service.Dtos.CommentDtoResp;
import com.project.edu_service.Dtos.LessonDto;
import com.project.edu_service.Dtos.LessonDtoResp;
import com.project.edu_service.mappers.CommentMapper;
import com.project.edu_service.mappers.LessonMapper;
import com.project.edu_service.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final CommentMapper commentMapper;

    @PostMapping("/add")
    public ResponseEntity<String> addLesson(@RequestBody LessonDto lessonDto) {
        lessonService.save(lessonMapper.toEntity(lessonDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/{title}")
    public ResponseEntity<String>setComment(@PathVariable String title, @RequestBody CommentDto commentDto) {
        lessonService.addComment(title, commentMapper.toEntity(commentDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/reply/{parentId}")
    public ResponseEntity<String>setReply(@PathVariable Long parentId, @RequestBody CommentDto commentDto) {
        lessonService.addReply(parentId, commentMapper.toEntity(commentDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/playlist/{title}")
    public ResponseEntity<String> addPlaylist(@PathVariable String title) {
        lessonService.addPLaylist(title);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/playlist/{playlistId}/lesson/{lessonId}")
    public ResponseEntity<String> addLessonToPlaylist(
            @PathVariable Long playlistId, @PathVariable Long lessonId) {

        lessonService.addLessonToPlaylist(playlistId, lessonId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/comments/{title}")
    public List<CommentDtoResp> getAllCommentsByArticleTitle(@PathVariable String title) {
        return lessonService.getAllCommentsByLessonTitle(title);
    }

    @GetMapping("/all")
    public List<LessonDtoResp> getAllLessons() {
        return lessonService.getAll();
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteArticleById(String title) {
        lessonService.deleteByTitle(title);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<String> deleteById(@PathVariable Long lessonId) {
        lessonService.deleteById(lessonId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
