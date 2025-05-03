package com.bertcoscia.BookReader.controllers;

import com.bertcoscia.BookReader.dto.requests.BookRequest;
import com.bertcoscia.BookReader.dto.responses.DataResponse;
import com.bertcoscia.BookReader.dto.responses.NewEntityResponse;
import com.bertcoscia.BookReader.entities.Book;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEntityResponse insertBook(
            @RequestBody @Validated BookRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));

            throw new BadRequestException(messages);
        }

        Book book = this.bookService.save(request);
        return new NewEntityResponse(true, book.getId(), "Book added successfully");
    }

    @GetMapping
    public DataResponse findAll() {
        return new DataResponse(true, this.bookService.findAll());
    }

    @GetMapping("/{id}")
    public DataResponse findById(@PathVariable("id") Long id) {
        return new DataResponse(true, this.bookService.findById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public DataResponse findByIsbn(@PathVariable("isbn") String isbn) {
        return new DataResponse(true, this.bookService.findByIsbn(isbn));
    }

    @GetMapping("/asin/{asin}")
    public DataResponse findByAsin(@PathVariable("asin") String asin) {
        return new DataResponse(true, this.bookService.findByAsin(asin));
    }

    @GetMapping("/author/{authorId}")
    public DataResponse findByAuthor(@PathVariable Long authorId) {
        return new DataResponse(true, this.bookService.findByAuthors_Id(authorId));
    }

    @GetMapping("/search")
    public DataResponse searchByAuthorNameOrSurname(@RequestParam String keyword) {
        return new DataResponse(true, this.bookService.searchByAuthorNameOrSurname(keyword));
    }

    @PutMapping("/{id}")
    public DataResponse update(
            @PathVariable Long id,
            @RequestBody @Validated BookRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        }

        return new DataResponse(true, this.bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public DataResponse deleteBook(@PathVariable Long id) {
        this.bookService.delete(id);
        return new DataResponse(true, "Book deleted successfully");
    }
}
