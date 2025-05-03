package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByAsin(String asin);

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByAsin(String asin);

    List<Book> findByAuthors_Id(Long idAuthor);

    @Query("""
            SELECT b FROM Book b
                JOIN b.authors a
                WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Book> searchByAuthorNameOrSurname(@Param("keyword") String keyword);
}
