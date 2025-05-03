package com.bertcoscia.BookReader.dto.responses;

public record NewEntityResponse(
        boolean success,
        Long id,
        String message) {
}
