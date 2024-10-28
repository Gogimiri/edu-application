package com.project.edu_service.service;

import com.project.edu_service.entityes.Users;
import com.project.edu_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registration(Users users) {

        userRepository.save(users);
    }

    //ПРОВЕРКА ПОЛЬЗОВАТЕЛЯ НА НАЛИЧИЕ:::
    public boolean userExists(String username) {

        return userRepository.existsByUsername(username);
    }

    //ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЯ ПО ЕГО username:::
    public Users getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    //ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЯ КОТОРЫЙ АВТОРИЗИРОВАН:::
    public Users getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    //ПОЛУЧЕНИЕ ВСЕХ ПОЛЬЗОВАТЕЛЕЙ НАПРЯМУЮ ИЗ РЕПОЗИТОРИЯ:::
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ ПО ЕГО username:::
    @Transactional
    public void deleteUserByUsername(String username) {

        userRepository.deleteByUsername(username);
    }

    //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЕЙ ПО ЕГО id:::
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }






}
