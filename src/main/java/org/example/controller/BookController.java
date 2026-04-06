package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.constant.MessageResponse;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.BookResponse;
import org.example.dto.request.BookRequest;
import org.example.service.BookService;
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
     public ApiResponse<BookResponse> addBook(@RequestBody BookRequest request) throws Exception {
        log.info("Adding book: {}", request);
        return new ApiResponse<>(MessageResponse.CREATE_BOOK_SUCCESS, bookService.addBook(request));
    }
@GetMapping
    public ApiResponse<List<BookResponse>> findAllBooks() throws Exception {
        log.info("Finding all books");
        return new ApiResponse<>(MessageResponse.GET_ALL_BOOK_SUCCESS, bookService.findAllBooks());
    }
}

