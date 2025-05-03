package com.bertcoscia.BookReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BookReaderApplication {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(BookReaderApplication.class, args);
    }

}
