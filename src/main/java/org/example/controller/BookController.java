package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.dto.response.BookResponse;
import org.example.dto.request.BookRequest;
import org.example.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

@PostMapping
     public BookResponse addBook(@RequestBody BookRequest request) throws Exception {
        log.info("Adding book: {}", request);
        return bookService.addBook(request);
    }
@GetMapping
    public List<BookResponse> findAllBooks() throws Exception {
        log.info("Finding all books");
        return bookService.findAllBooks();
    }
}

