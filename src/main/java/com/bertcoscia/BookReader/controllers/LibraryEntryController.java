package com.bertcoscia.BookReader.controllers;

import com.bertcoscia.BookReader.dto.requests.LibraryEntryRequest;
import com.bertcoscia.BookReader.dto.responses.DataResponse;
import com.bertcoscia.BookReader.dto.responses.MessageResponse;
import com.bertcoscia.BookReader.dto.responses.NewEntityResponse;
import com.bertcoscia.BookReader.entities.LibraryEntry;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.services.LibraryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("library")
public class LibraryEntryController {

    @Autowired
    private LibraryEntryService libraryEntryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEntityResponse insertLibraryEntry(
            @RequestBody @Validated LibraryEntryRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));

            throw new BadRequestException(messages);
        }

        LibraryEntry libraryEntry = this.libraryEntryService.save(request);

        return new NewEntityResponse(true, libraryEntry.getId(), "Library entry added successfully");
    }

    @GetMapping
    public DataResponse findAll() {
        return new DataResponse(true, this.libraryEntryService.findAll());
    }

    @GetMapping("/user/{idUser}")
    public DataResponse findAllByUserId(@PathVariable("idUser") Long idUser) {
        return new DataResponse(true, this.libraryEntryService.findAllByUserId(idUser));
    }

    @GetMapping("/{id}")
    public DataResponse findById(@PathVariable("id") Long id) {
        return new DataResponse(true, this.libraryEntryService.findById(id));
    }

    @PostMapping("/{id}")
    public DataResponse update(
            @PathVariable("id") Long id,
            @RequestBody @Validated LibraryEntryRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));

            throw new BadRequestException(messages);
        }

        return new DataResponse(true, this.libraryEntryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable("id") Long id) {
        return new MessageResponse(true, "Library entry deleted successfully");
    }
}
