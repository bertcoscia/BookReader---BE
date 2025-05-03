package com.bertcoscia.BookReader.dto.requests;

import com.bertcoscia.BookReader.entities.BookFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.Nullable;

import java.util.List;

public record BookRequest(

        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotEmpty(message = "At least one author is required")
        List<Long> authorIds,

        @Pattern(
                regexp = "^(|\\d{10}|\\d{13})$",
                message = "ISBN must be 10 or 13 digits, or empty"
        )
        @Nullable
        String isbn,

        @Pattern(regexp = "^(|\\w{10})$", message = "ASIN must be exactly 10 alphanumeric characters or omitted")
        @Nullable
        String asin,

        Integer pages,

        @NotBlank(message = "Publication date is required")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in format yyyy-MM-dd")
        String publicationDate,

        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Book format is required")
        BookFormat bookFormat

) {
}