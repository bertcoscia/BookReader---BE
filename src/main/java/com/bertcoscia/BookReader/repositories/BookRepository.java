package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
