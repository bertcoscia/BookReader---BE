package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.ReadingSessionRequest;
import com.bertcoscia.BookReader.entities.ReadingSession;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.exceptions.NotFoundException;
import com.bertcoscia.BookReader.mappers.ReadingSessionMapper;
import com.bertcoscia.BookReader.repositories.ReadingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ReadingSessionService {

    @Autowired
    private ReadingSessionRepository readingSessionRepository;

    @Autowired
    private ReadingSessionMapper readingSessionMapper;

    public ReadingSession save(ReadingSessionRequest request) {
        LocalDate startedAt;
        LocalDate finishedAt = null;

        try {
            startedAt = LocalDate.parse(request.startedAt());
            if (request.finishedAt() != null) {
                finishedAt = LocalDate.parse(request.finishedAt());
            }
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Please use ISO format (yyyy-MM-dd).");
        }

        if (this.readingSessionRepository.existsByLibraryEntryIdAndFinishedAtIsNull(request.idLibraryEntry()))
            throw new BadRequestException("There is already an active reading session for this book. Please finish it before starting a new one.");

        if (finishedAt != null && this.readingSessionRepository
                .existsByLibraryEntryIdAndStartedAtLessThanEqualAndFinishedAtGreaterThanEqual(
                        request.idLibraryEntry(), finishedAt, startedAt))
            throw new BadRequestException("The selected date range overlaps with an existing reading session for this book.");

        if (finishedAt != null && this.readingSessionRepository
                .existsByLibraryEntryIdAndStartedAtAndFinishedAt(
                        request.idLibraryEntry(), finishedAt, startedAt))
            throw new BadRequestException("A reading session with the same date range already exists for this book.");

        return this.readingSessionRepository.save(this.readingSessionMapper.toEntity(request));
    }

    public ReadingSession findById(Long id) {
        return this.readingSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public List<ReadingSession> findAll() {
        return this.readingSessionRepository.findAll();
    }

    public List<ReadingSession> findAllByIdUser(Long idUser) {
        return this.readingSessionRepository.findAllByLibraryEntryUserId(idUser);
    }

    public ReadingSession update(Long id, ReadingSessionRequest request) {
        ReadingSession readingSessionToUpdate = this.findById(id);

        LocalDate startedAt;
        LocalDate finishedAt = null;

        try {
            startedAt = LocalDate.parse(request.startedAt());
            if (request.finishedAt() != null) {
                finishedAt = LocalDate.parse(request.finishedAt());
            }
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Please use ISO format (yyyy-MM-dd).");
        }

        if (this.readingSessionRepository.existsByLibraryEntryIdAndFinishedAtIsNullAndIdNot(
                request.idLibraryEntry(), id)) {
            throw new BadRequestException("There is already an active reading session for this book. Please finish it before updating.");
        }

        if (finishedAt != null && this.readingSessionRepository
                .existsByLibraryEntryIdAndStartedAtLessThanEqualAndFinishedAtGreaterThanEqualAndIdNot(
                        request.idLibraryEntry(), finishedAt, startedAt, id)) {
            throw new BadRequestException("The selected date range overlaps with another reading session.");
        }

        if (finishedAt != null && this.readingSessionRepository
                .existsByLibraryEntryIdAndStartedAtAndFinishedAtAndIdNot(
                        request.idLibraryEntry(), startedAt, finishedAt, id)) {
            throw new BadRequestException("A reading session with the same date range already exists.");
        }

        return this.readingSessionRepository.save(
                this.readingSessionMapper.update(readingSessionToUpdate, request)
        );
    }

    public void delete(Long id) {
        this.readingSessionRepository.delete(this.findById(id));
    }

}
