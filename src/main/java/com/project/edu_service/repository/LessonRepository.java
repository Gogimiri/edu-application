package com.project.edu_service.repository;


import com.project.edu_service.entityes.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Optional<Lesson> findByTitle(String title);
    void deleteByTitle(String title);
}
