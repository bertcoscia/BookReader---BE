package com.bertcoscia.BookReader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reading_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_library_entry", nullable = false)
    private LibraryEntry libraryEntry;

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "finished_at")
    private LocalDate finishedAt;

    @Column(name = "current_page")
    private Integer currentPage;
}
