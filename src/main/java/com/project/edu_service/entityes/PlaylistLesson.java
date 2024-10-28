package com.project.edu_service.entityes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlaylistLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistCourseId;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Lesson lesson;
}
