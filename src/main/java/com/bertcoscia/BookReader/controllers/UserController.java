package com.bertcoscia.BookReader.controllers;

import com.bertcoscia.BookReader.dto.responses.DataResponse;
import com.bertcoscia.BookReader.dto.responses.MessageResponse;
import com.bertcoscia.BookReader.entities.User;
import com.bertcoscia.BookReader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public DataResponse findAll() {
        return new DataResponse(true, this.userService.findAll());
    }

    @GetMapping("/{id}")
    public DataResponse findById(@PathVariable("id") Long id) {
        return new DataResponse(true, this.userService.findById(id));
    }

    @GetMapping("/me")
    public DataResponse getMyProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return new DataResponse(true, this.userService.findById(currentAuthenticatedUser.getId()));
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponse deleteMyProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.delete(currentAuthenticatedUser.getId());
        return new MessageResponse(true, "User deleted successfully");
    }

    @PutMapping("/me")
    public DataResponse updateMyProfile(
            @AuthenticationPrincipal User currentAuthenticatedUser,
            @RequestBody User userUpdated
    ) {
        return new DataResponse(true, this.userService.update(currentAuthenticatedUser.getId(), userUpdated));
    }


}
