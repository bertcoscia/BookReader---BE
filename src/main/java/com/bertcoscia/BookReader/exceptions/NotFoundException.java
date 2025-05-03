package com.bertcoscia.BookReader.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id) {
        super("Could not find resource with id " + id);
    }
}
