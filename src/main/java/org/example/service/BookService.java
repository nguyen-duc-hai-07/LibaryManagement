package org.example.service;

import org.example.dto.response.BookResponse;
import org.example.dto.request.BookRequest;

import java.util.List;

public interface BookService {

    BookResponse addBook(BookRequest request) throws Exception;

    List<BookResponse> findAllBooks() throws Exception;
}
