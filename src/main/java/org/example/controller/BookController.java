package org.example.controller;


import org.example.dto.response.BookResponse;
import org.example.dto.request.BookRequest;
import org.example.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

@PostMapping
     public BookResponse addBook(@RequestBody BookRequest request) throws Exception {
        return bookService.addBook(request);
    }
@GetMapping
    public List<BookResponse> findAllBooks() throws Exception {
        return bookService.findAllBooks();
    }
}

