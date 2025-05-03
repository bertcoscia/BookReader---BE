package com.bertcoscia.BookReader.mappers;

import com.bertcoscia.BookReader.dto.requests.AuthorRequest;
import com.bertcoscia.BookReader.entities.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        Author author = new Author();
        populateFromRequest(author, request);
        return author;
    }

    public Author update(Author authorToUpdate, AuthorRequest request) {
        populateFromRequest(authorToUpdate, request);
        return authorToUpdate;
    }

    private void populateFromRequest(Author author, AuthorRequest request) {
        author.setName(request.name());
        author.setSurname(request.surname());
        author.setBio(request.bio());
    }
}
