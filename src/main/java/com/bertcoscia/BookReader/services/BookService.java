package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.BookRequest;
import com.bertcoscia.BookReader.entities.Book;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.exceptions.NotFoundException;
import com.bertcoscia.BookReader.mappers.BookMapper;
import com.bertcoscia.BookReader.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorService authorService;


    public Book save(BookRequest request) {
        if (request.isbn() == null && request.asin() == null)
            throw new BadRequestException("At least one of ISBN or ASIN is required.");

        if (request.isbn() != null && !request.isbn().isBlank() && this.bookRepository.existsByIsbn(request.isbn()))
            throw new BadRequestException("There is already a book with ISBN " + request.isbn());

        if (request.asin() != null && !request.asin().isBlank() && this.bookRepository.existsByAsin(request.asin()))
            throw new BadRequestException("There is already a book with ASIN " + request.asin());

        return this.bookRepository.save(this.bookMapper.toEntity(request));
    }

    public Book findById(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Book findByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Cannot find book with ISBN " + isbn));
    }

    public Book findByAsin(String asin) {
        return this.bookRepository.findByAsin(asin)
                .orElseThrow(() -> new NotFoundException("Cannot find book with ISBN " + asin));
    }

    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public List<Book> findByAuthors_Id(Long idAuthor) {
        return this.bookRepository.findByAuthors_Id(idAuthor);
    }

    public List<Book> searchByAuthorNameOrSurname(String keyword) {
        return this.bookRepository.searchByAuthorNameOrSurname(keyword);
    }

    public Book update(Long id, BookRequest request) {
        Book bookToUpdate = this.findById(id);

        if (request.isbn() == null && request.asin() == null)
            throw new BadRequestException("At least one of ISBN or ASIN is required.");

        if (request.isbn() != null && !request.isbn().isBlank()) {
            Book bookWithSameIsbn = this.bookRepository.findByIsbn(request.isbn()).orElse(null);

            if (bookWithSameIsbn != null && !bookWithSameIsbn.getId().equals(id))
                throw new BadRequestException("There is already a book with ISBN " + request.isbn());
        }

        if (request.asin() != null && !request.asin().isBlank()) {
            Book bookWithSameAsin = this.bookRepository.findByAsin(request.asin()).orElse(null);

            if (bookWithSameAsin != null && !bookWithSameAsin.getId().equals(id))
                throw new BadRequestException("There is already a book with ASIN " + request.asin());
        }

        this.bookMapper.update(bookToUpdate, request);
        return this.bookRepository.save(bookToUpdate);
    }

    public void delete(Long id) {
        this.bookRepository.delete(this.findById(id));
    }
}
