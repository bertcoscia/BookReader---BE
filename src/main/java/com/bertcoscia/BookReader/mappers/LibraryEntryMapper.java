package com.bertcoscia.BookReader.mappers;

import com.bertcoscia.BookReader.dto.requests.LibraryEntryRequest;
import com.bertcoscia.BookReader.entities.Book;
import com.bertcoscia.BookReader.entities.LibraryEntry;
import com.bertcoscia.BookReader.entities.User;
import com.bertcoscia.BookReader.services.BookService;
import com.bertcoscia.BookReader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LibraryEntryMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public LibraryEntry toEntity(LibraryEntryRequest request) {
        LibraryEntry libraryEntry = new LibraryEntry();
        populateFromRequest(libraryEntry, request);
        return libraryEntry;
    }

    public LibraryEntry update(LibraryEntry libraryEntryToUpdate, LibraryEntryRequest request) {
        populateFromRequest(libraryEntryToUpdate, request);
        return libraryEntryToUpdate;
    }

    private void populateFromRequest(LibraryEntry libraryEntry, LibraryEntryRequest request) {
        User user = this.userService.findById(request.idUser());
        libraryEntry.setUser(user);

        Book book = this.bookService.findById(request.idBook());
        libraryEntry.setBook(book);

        libraryEntry.setLibraryEntryStatus(request.libraryEntryStatus());
        libraryEntry.setAddedAt(LocalDate.now());
    }
}
