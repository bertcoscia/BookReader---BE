package com.bertcoscia.BookReader.controllers;

import com.bertcoscia.BookReader.services.ReadingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/readingSession")
public class ReadingSessionController {

    @Autowired
    private ReadingSessionService readingSessionService;

    //TODO: READING SESSION CONTROLLER
}
