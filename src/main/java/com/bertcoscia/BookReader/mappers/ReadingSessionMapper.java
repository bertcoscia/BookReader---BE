package com.bertcoscia.BookReader.mappers;

import com.bertcoscia.BookReader.dto.requests.ReadingSessionRequest;
import com.bertcoscia.BookReader.entities.LibraryEntry;
import com.bertcoscia.BookReader.entities.ReadingSession;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.services.LibraryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class ReadingSessionMapper {

    @Autowired
    private LibraryEntryService libraryEntryService;

    public ReadingSession toEntity(ReadingSessionRequest request) {
        ReadingSession readingSession = new ReadingSession();
        populateFromRequest(readingSession, request);
        return readingSession;
    }

    public ReadingSession update(ReadingSession readingSessionToUpdate, ReadingSessionRequest request) {
        populateFromRequest(readingSessionToUpdate, request);
        return readingSessionToUpdate;
    }

    private void populateFromRequest(ReadingSession readingSession, ReadingSessionRequest request) {
        LibraryEntry libraryEntry = this.libraryEntryService.findById(request.idLibraryEntry());
        readingSession.setLibraryEntry(libraryEntry);

        if (request.currentPage() != null)
            readingSession.setCurrentPage(request.currentPage());
        else readingSession.setCurrentPage(0);

        try {
            LocalDate startDate = LocalDate.parse(request.startedAt());
            if (startDate.isAfter(LocalDate.now()))
                throw new BadRequestException("Start date cannot be in the future");
            readingSession.setStartedAt(startDate);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid start date");
        }

        if (request.finishedAt() != null) {
            try {
                LocalDate endDate = LocalDate.parse(request.finishedAt());
                if (endDate.isAfter(LocalDate.now()))
                    throw new BadRequestException("Start date cannot be in the future");
                readingSession.setStartedAt(endDate);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Invalid end date");
            }
        }
    }
}
