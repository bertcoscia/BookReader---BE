package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.AuthorRequest;
import com.bertcoscia.BookReader.entities.Author;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.exceptions.NotFoundException;
import com.bertcoscia.BookReader.mappers.AuthorMapper;
import com.bertcoscia.BookReader.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

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

    public Set<Author> findAllById(List<Long> idList) {
        return new HashSet<>(this.authorRepository.findAllById(idList));
    }

    public List<Author> searchByNameOrSurname(String keyword) {
        return this.authorRepository.searchByNameOrSurname(keyword);
    }

    public Author update(Long id, AuthorRequest request) {
        Author authorToUpdate = this.findById(id);

        if (authorRepository.existsByNameAndSurname(request.name(), request.surname())
                && !authorToUpdate.getName().equals(request.name())
                && !authorToUpdate.getSurname().equals(request.surname())) {
            throw new BadRequestException("Another author with the same name and surname already exists");
        }

        return this.authorMapper.update(authorToUpdate, request);
    }

    public void delete(Long id) {
        this.authorRepository.delete(this.findById(id));
    }
}
