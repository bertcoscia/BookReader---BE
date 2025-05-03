package com.bertcoscia.BookReader.mappers;

import com.bertcoscia.BookReader.dto.requests.AuthorRequest;
import com.bertcoscia.BookReader.entities.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        Author author = new Author();

        author.setName(request.name());
        author.setSurname(request.surname());
        author.setBio(request.bio());

        return author;
    }

    public Author update(Author authorToUpdate, Author authorUpdated) {
        authorToUpdate.setName(authorUpdated.getName());
        authorToUpdate.setSurname(authorUpdated.getSurname());
        authorToUpdate.setBio(authorUpdated.getBio());

        return authorToUpdate;
    }
}
