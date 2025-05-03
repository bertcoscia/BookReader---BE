package com.bertcoscia.BookReader.exceptions;

import com.bertcoscia.BookReader.dto.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponse handleBadRequest(BadRequestException ex) {
        return new ErrorResponse(false, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorResponse handleUnauthorized(UnauthorizedException ex) {
        return new ErrorResponse(false, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorResponse handleNotFound(NotFoundException ex) {
        return new ErrorResponse(false, ex.getMessage(), LocalDateTime.now());
    }
}
