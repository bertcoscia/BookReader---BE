package com.bertcoscia.BookReader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String username;

    @Column(name = "url_avatar")
    private String urlAvatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LibraryEntry> libraryEntries = new HashSet<>();

}
