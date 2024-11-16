package com.project.edu_service.entityes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Playlist {
    @UUID
    private Long playlistId;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
