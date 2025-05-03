package com.bertcoscia.BookReader.dto.responses;

import java.time.LocalDateTime;

public record ErrorResponse(
        boolean success,
        String message,
        LocalDateTime timestamp
) {
}
