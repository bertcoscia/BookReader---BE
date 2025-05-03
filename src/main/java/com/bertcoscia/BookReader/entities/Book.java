package com.bertcoscia.BookReader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_author")
    )
    private Set<Author> authors;

    private String isbn;

    private String isbn13;

    private Integer pages;

    @Column(name = "url_cover")
    private String urlCover;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LibraryEntry> libraryEntries = new HashSet<>();
}
