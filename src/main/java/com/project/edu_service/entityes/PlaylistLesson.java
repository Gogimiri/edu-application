package com.project.edu_service.entityes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Entity
@Getter
@Setter
public class PlaylistLesson {

    @UUID
    private Long playlistCourseId;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Lesson lesson;
}
