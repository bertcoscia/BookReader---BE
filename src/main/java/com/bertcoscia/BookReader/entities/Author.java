package com.bertcoscia.BookReader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String surname;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "url_avatar")
    private String urlAvatar;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}
