package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByNameAndSurname(String name, String surname);

    @Query("""
                SELECT a FROM Author a
                WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Author> searchByNameOrSurname(String keyword);
}
