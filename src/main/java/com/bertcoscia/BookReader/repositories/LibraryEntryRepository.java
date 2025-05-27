package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.LibraryEntry;
import com.bertcoscia.BookReader.entities.LibraryEntryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryEntryRepository extends JpaRepository<LibraryEntry, Long> {

    boolean existsByUserIdAndBookIdAndLibraryEntryStatus(Long idUser, Long idBook, LibraryEntryStatus libraryEntryStatus);

    boolean existsByUserIdAndBookIdAndLibraryEntryStatusAndIdNot(Long userId, Long bookId, LibraryEntryStatus libraryEntryStatus, Long id);

    List<LibraryEntry> findAllByUserId(Long idUser);
}