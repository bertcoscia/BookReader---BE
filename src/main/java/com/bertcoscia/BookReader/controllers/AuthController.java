package com.bertcoscia.BookReader.controllers;

import com.bertcoscia.BookReader.dto.requests.LoginRequest;
import com.bertcoscia.BookReader.dto.requests.UserRequest;
import com.bertcoscia.BookReader.dto.responses.LoginResponse;
import com.bertcoscia.BookReader.dto.responses.NewEntityResponse;
import com.bertcoscia.BookReader.entities.User;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.services.AuthService;
import com.bertcoscia.BookReader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEntityResponse signup(
            @RequestBody @Validated UserRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));

            throw new BadRequestException(messages);
        }

        User user = this.userService.save(request);
        return new NewEntityResponse(true, user.getId(), "User created successfully");
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Validated LoginRequest request,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));

            throw new BadRequestException(messages);
        }

        return new LoginResponse(true, this.authService.generateToken(request));
    }
}
