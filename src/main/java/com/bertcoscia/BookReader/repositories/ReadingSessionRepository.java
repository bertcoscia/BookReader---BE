package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {
}
