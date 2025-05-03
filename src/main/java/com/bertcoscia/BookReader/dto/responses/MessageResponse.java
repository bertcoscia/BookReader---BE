package com.bertcoscia.BookReader.dto.responses;

public record MessageResponse(
        boolean success,
        String message
) {
}
