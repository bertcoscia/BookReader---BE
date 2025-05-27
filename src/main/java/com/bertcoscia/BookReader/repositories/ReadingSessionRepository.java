package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {

    boolean existsByLibraryEntryIdAndFinishedAtIsNull(Long libraryEntryId);

    boolean existsByLibraryEntryIdAndStartedAtLessThanEqualAndFinishedAtGreaterThanEqual(
            Long libraryEntryId,
            LocalDate finishedAt,
            LocalDate startedAt
    );

    boolean existsByLibraryEntryIdAndStartedAtAndFinishedAt(Long libraryEntryId, LocalDate startedAt, LocalDate finishedAt);

    List<ReadingSession> findAllByLibraryEntryUserId(Long idUser);

    boolean existsByLibraryEntryIdAndFinishedAtIsNullAndIdNot(Long libraryEntryId, Long id);

    boolean existsByLibraryEntryIdAndStartedAtLessThanEqualAndFinishedAtGreaterThanEqualAndIdNot(
            Long libraryEntryId, LocalDate finishedAt, LocalDate startedAt, Long id
    );

    boolean existsByLibraryEntryIdAndStartedAtAndFinishedAtAndIdNot(
            Long libraryEntryId, LocalDate startedAt, LocalDate finishedAt, Long id
    );
}
