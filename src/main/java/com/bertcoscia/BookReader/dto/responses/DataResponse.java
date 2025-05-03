package com.bertcoscia.BookReader.dto.responses;

public record DataResponse(
        boolean success,
        Object data
) {
}
