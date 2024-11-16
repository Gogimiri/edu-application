package com.project.edu_service.service;

import com.project.edu_service.Dtos.CommentDtoResp;
import com.project.edu_service.Dtos.LessonDtoResp;
import com.project.edu_service.entityes.Comment;
import com.project.edu_service.entityes.Lesson;
import com.project.edu_service.entityes.Playlist;
import com.project.edu_service.entityes.PlaylistLesson;
import com.project.edu_service.repository.CommentRepositry;
import com.project.edu_service.repository.LessonRepository;
import com.project.edu_service.repository.PlaylistLessonRepository;
import com.project.edu_service.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final UserService userService;
    private final LessonRepository lessonRepository;
    private final CommentRepositry commentRepositry;private final PlaylistRepository playlistRepository;
    private final PlaylistLessonRepository playlistLessonRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**ДОБАВЛЕНИЕ УРОКА**/
    @Transactional
    public void save(Lesson lesson) {
        lesson.setUsers(userService.getCurrentUser());
        lesson.setCreatedAt(LocalDateTime.now());

        lessonRepository.save(lesson);
    }

    /**ДОБАВЛЕНИЕ КОММЕНТАРИЯ**/
    @Transactional
    public void addComment(String title, Comment comment) {
        Lesson lesson = lessonRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("lesson not found"));

        comment.setLesson(lesson);
        comment.setUsers(userService.getCurrentUser());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepositry.save(comment);
    }

    /**ДОБАВЛЕНИЕ ОТВЕТА НА КОММЕНТАРИЙ**/
    @Transactional
    public void addReply(Long parentId, Comment reply) {
        Comment parantComment = commentRepositry.findById(parentId)
                .orElseThrow(() -> new RuntimeException("parent не найден"));

        reply.setLesson(parantComment.getLesson());
        reply.setUsers(userService.getCurrentUser());
        reply.setCreatedAt(LocalDateTime.now());
        reply.setParent(parantComment);

        commentRepositry.save(reply);
    }

    /**ДОБАВЛЕНИЕ ПЛЭЙЛИСТА**/
    @Transactional
    public void addPLaylist(String title) {
        logger.info("title insert into methot: {}", title);
        Playlist playlist = new Playlist();
        playlist.setTitle(title);
        playlist.setUsers(userService.getCurrentUser());
        playlist.setCreatedAt(LocalDateTime.now());

        playlistRepository.save(playlist);
    }

    /**ДОБАВЛЕНИЕ УРОКА В ПЛЭЙЛИСТ**/
    @Transactional
    public void addLessonToPlaylist(Long playlistId, Long lessonId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        PlaylistLesson playlistLesson = new PlaylistLesson();
        playlistLesson.setPlaylist(playlist);
        playlistLesson.setLesson(lesson);

        playlistLessonRepository.save(playlistLesson);
    }

    /**ПОЛУЧЕНИЕ ВСЕХ КОММЕНТАРИЕВ ПО ЗАГОЛОВКУ СТАТЬИ**/
    @Transactional
    public List<CommentDtoResp> getAllCommentsByLessonTitle(String title) {
        Lesson lesson = lessonRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        List<Comment> comments = lesson.getComments();
        List<CommentDtoResp> commentDtoResps = new ArrayList<>();

        for (Comment comment : comments) {
            String parentUsername = comment.getParent() != null ? comment.getParent().getUsers().getUsername() : null;

            CommentDtoResp commentDto = new CommentDtoResp(
                    comment.getUsers().getUsername(),
                    lesson.getTitle(),
                    parentUsername,
                    comment.getContent()
            );
            commentDtoResps.add(commentDto);
        }
        return commentDtoResps;
    }

    /**ПОЛУЧЕНИЕ УРОКОВ ПО ЗАГОЛОВКУ**/
    @Transactional
    public void deleteByTitle(String title) {
        lessonRepository.deleteByTitle(title);
    }

    /**УДАЛЕНИЕ УРОКА ПО id**/
    @Transactional
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    /**ПОЛУЧЕНИЕ ВСЕХ УРОКОВ**/
    @Transactional
    public List<LessonDtoResp> getAll() {
        logger.info("into get All method");
        List<Lesson> lessons = lessonRepository.findAll();

        return lessons.stream()
                .map(lesson -> new LessonDtoResp(
                        lesson.getTitle(),
                        lesson.getDescription(),
                        lesson.getVideoLink(),
                        (long) lesson.getComments().size(),
                        lesson.getUsers() != null ? lesson.getUsers().getUsername() : null
                ))
                .collect(Collectors.toList());
    }

    /**ПОЛУЧЕНИЕ ВСЕХ УРОКОВ НАПРЯМУЮ ИЗ РЕПОЗИТОРИЕВ**/
    public List<Lesson>getAllWithAllInfo() {
        return lessonRepository.findAll();
    }
}
