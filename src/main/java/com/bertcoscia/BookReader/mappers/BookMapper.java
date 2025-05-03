package com.bertcoscia.BookReader.mappers;

import com.bertcoscia.BookReader.dto.requests.BookRequest;
import com.bertcoscia.BookReader.entities.Author;
import com.bertcoscia.BookReader.entities.Book;
import com.bertcoscia.BookReader.entities.BookFormat;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

@Component
public class BookMapper {

    @Autowired
    private AuthorService authorService;

    public Book toEntity(BookRequest request) {
        Book book = new Book();
        populateFromRequest(book, request);
        return book;
    }

    public Book update(Book bookToUpdate, BookRequest request) {
        populateFromRequest(bookToUpdate, request);
        return bookToUpdate;
    }

    private void populateFromRequest(Book book, BookRequest request) {
        book.setTitle(request.title());

        Set<Author> authors = this.authorService.findAllById(request.authorIds());
        if (authors.isEmpty())
            throw new BadRequestException("No valid authors found for given IDs");
        book.setAuthors(authors);

        if (request.isbn() != null && !request.isbn().isBlank())
            book.setIsbn(request.isbn());

        if (request.asin() != null && !request.asin().isBlank())
            book.setAsin(request.asin());

        if (request.bookFormat() != BookFormat.AUDIOBOOK) {
            if (request.pages() == null || request.pages() < 1)
                throw new BadRequestException("Pages must be at least 1 for non-audiobook formats");

            book.setPages(request.pages());
        } else book.setPages(null);

        try {
            LocalDate date = LocalDate.parse(request.publicationDate());
            if (date.isAfter(LocalDate.now()))
                throw new BadRequestException("Publication date cannot be in the future");
            book.setPublicationDate(date);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Use yyyy-MM-dd.");
        }

        book.setDescription(request.description());
        book.setBookFormat(request.bookFormat());
    }
}
