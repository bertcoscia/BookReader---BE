package com.bertcoscia.BookReader.repositories;

import com.bertcoscia.BookReader.entities.ReadingProgressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingProgressLogRepository extends JpaRepository<ReadingProgressLog, Long> {
}
