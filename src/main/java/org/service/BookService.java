package org.service;

import org.dto.response.BookResponse;
import org.dto.request.BookRequest;

import java.util.List;

public interface BookService {

    BookResponse addBook(BookRequest request) throws Exception;

    List<BookResponse> findAllBooks() throws Exception;
}
