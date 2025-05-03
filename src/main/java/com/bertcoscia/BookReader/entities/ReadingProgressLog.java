package com.bertcoscia.BookReader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reading_progress_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingProgressLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_reading_session", nullable = false)
    private ReadingSession readingSession;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "logged_at")
    private LocalDateTime loggedAt;
}
