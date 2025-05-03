package com.bertcoscia.BookReader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "library_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    private LibraryEntryStatus libraryEntryStatus;

    @Column(name = "added_at")
    private LocalDate addedAt;
}
