package com.bertcoscia.BookReader.dto.requests;

import com.bertcoscia.BookReader.entities.LibraryEntryStatus;
import jakarta.validation.constraints.NotNull;

public record LibraryEntryRequest(
        @NotNull(message = "User id cannot be null")
        Long idUser,

        @NotNull(message = "Book id cannot be null")
        Long idBook,

        @NotNull(message = "Library entry status cannot be null")
        LibraryEntryStatus libraryEntryStatus
) {
}
