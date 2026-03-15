package service;

import dto.BookResponse;
import model.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book) throws Exception;

    List<BookResponse> findAllBooks() throws Exception;
}
