package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.AuthorRequest;
import com.bertcoscia.BookReader.entities.Author;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.exceptions.NotFoundException;
import com.bertcoscia.BookReader.mappers.AuthorMapper;
import com.bertcoscia.BookReader.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    public Author save(AuthorRequest request) {
        if (this.authorRepository.existsByNameAndSurname(request.name(), request.surname()))
            throw new BadRequestException("There is already an author called " + request.name() + " " + request.surname());

        return this.authorRepository.save(this.authorMapper.toEntity(request));
    }

    public Author findById(Long id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    public List<Author> searchByNameOrSurname(String keyword) {
        return this.authorRepository.searchByNameOrSurname(keyword);
    }

    public Author update(Long id, Author authorUpdated) {
        Author authorToUpdate = this.findById(id);

        if (this.authorRepository.existsByNameAndSurname(authorUpdated.getName(), authorUpdated.getSurname()))
            throw new BadRequestException("There is already an author called " + authorUpdated.getName() + " " + authorUpdated.getSurname());

        return this.authorMapper.update(authorToUpdate, authorUpdated);
    }

    public void delete(Long id) {
        this.authorRepository.delete(this.findById(id));
    }
}
