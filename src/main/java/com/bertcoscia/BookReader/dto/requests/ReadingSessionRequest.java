package com.bertcoscia.BookReader.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReadingSessionRequest(
        @NotNull(message = "Library entry cannot be null")
        Long idLibraryEntry,

        @NotBlank(message = "Start date is required")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in format yyyy-MM-dd")
        String startedAt,

        String finishedAt,

        Integer currentPage
) {
}
