package com.bertcoscia.BookReader.controllers;

import com.bertcoscia.BookReader.dto.requests.AuthorRequest;
import com.bertcoscia.BookReader.dto.responses.DataResponse;
import com.bertcoscia.BookReader.dto.responses.MessageResponse;
import com.bertcoscia.BookReader.dto.responses.NewEntityResponse;
import com.bertcoscia.BookReader.entities.Author;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEntityResponse insertAuthor(
            @RequestBody @Validated AuthorRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));

            throw new BadRequestException(messages);
        }

        Author author = this.authorService.save(request);

        return new NewEntityResponse(true, author.getId(), "Author added successfully");
    }

    @GetMapping
    public DataResponse findAll() {
        return new DataResponse(true, this.authorService.findAll());
    }

    @GetMapping("/{id}")
    public DataResponse findById(@PathVariable("id") Long id) {
        return new DataResponse(true, this.authorService.findById(id));
    }

    @GetMapping("/search")
    public DataResponse searchByNameOrSurname(@RequestParam String keyword) {
        return new DataResponse(true, this.authorService.searchByNameOrSurname(keyword));
    }

    @PostMapping("/{id}")
    public DataResponse update(@PathVariable("id") Long id, @RequestBody Author authorUpdated) {
        return new DataResponse(true, this.authorService.update(id, authorUpdated));
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable("id") Long id) {
        return new MessageResponse(true, "Auth");
    }
}
