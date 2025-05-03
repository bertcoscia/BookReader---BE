package com.bertcoscia.BookReader.dto.responses;

public record LoginResponse(
        boolean succcess,
        String token
) {
}
