package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.LibraryEntryRequest;
import com.bertcoscia.BookReader.entities.LibraryEntry;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.exceptions.NotFoundException;
import com.bertcoscia.BookReader.mappers.LibraryEntryMapper;
import com.bertcoscia.BookReader.repositories.LibraryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryEntryService {

    @Autowired
    private LibraryEntryRepository libraryEntryRepository;

    @Autowired
    private LibraryEntryMapper libraryEntryMapper;

    public LibraryEntry save(LibraryEntryRequest request) {
        if (this.libraryEntryRepository.existsByUserIdAndBookIdAndLibraryEntryStatus(request.idUser(), request.idUser(), request.libraryEntryStatus()))
            throw new BadRequestException("There is already a library entry for the given data");

        return this.libraryEntryRepository.save(this.libraryEntryMapper.toEntity(request));
    }

    public LibraryEntry findById(Long id) {
        return this.libraryEntryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public List<LibraryEntry> findAll() {
        return this.libraryEntryRepository.findAll();
    }

    public List<LibraryEntry> findAllByUserId(Long id) {
        return this.libraryEntryRepository.findAllByUserId(id);
    }

    public LibraryEntry update(Long id, LibraryEntryRequest request) {
        LibraryEntry libraryEntry = this.findById(id);

        boolean duplicateExists = this.libraryEntryRepository
                .existsByUserIdAndBookIdAndLibraryEntryStatusAndIdNot(
                        request.idUser(),
                        request.idBook(),
                        request.libraryEntryStatus(),
                        id
                );

        if (duplicateExists)
            throw new BadRequestException("A similar library entry already exists");

        return this.libraryEntryMapper.update(libraryEntry, request);
    }

    public void delete(Long id) {
        this.libraryEntryRepository.delete(this.findById(id));
    }
}
