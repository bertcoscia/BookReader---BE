package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.LibraryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryEntryRepository extends JpaRepository<LibraryEntry, Long> {
}
