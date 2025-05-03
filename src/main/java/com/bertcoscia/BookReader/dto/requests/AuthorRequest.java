package com.bertcoscia.BookReader.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Surname cannot be blank")
        String surname,

        String bio
) {
}
