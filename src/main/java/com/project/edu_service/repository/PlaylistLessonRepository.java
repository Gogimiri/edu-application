package com.project.edu_service.repository;

import com.project.edu_service.entityes.PlaylistLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistLessonRepository extends JpaRepository<PlaylistLesson, Long> {
}
