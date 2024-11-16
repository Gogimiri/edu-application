package com.project.edu_service.controllers;

import com.project.edu_service.entityes.Users;
import com.project.edu_service.repository.CommentRepositry;
import com.project.edu_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;
    private final CommentRepositry commentRepositry;

    /**ПОЛУЧЕНИЕ ВСЕХПОЛЬЗОВАТЕЛЕЙ НАПРЯМУЮ ИЗ РЕПОЗИТОРИЯ**/
    @GetMapping("/all")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    /**УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ ПО username**/
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);

        return new  ResponseEntity<>(HttpStatus.OK);
    }

    /**УДАЛЕНИЕ ПОЛЬЗХОВАТЕЛЯ ПО id**/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**УДАЛЕНИЕ КОММЕНТАРИЯ ПО ЕГО id**/
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long id) {
        commentRepositry.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
