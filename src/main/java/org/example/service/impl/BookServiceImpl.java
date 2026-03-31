package org.example.service.impl;

import org.example.dto.request.BookRequest;
import org.example.service.BookService;
import org.example.config.DBConnectionPool;
import org.example.dao.BookDAO;
import org.example.dto.response.BookResponse;
import org.example.model.Book;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public BookResponse addBook(BookRequest request) throws Exception {
        Connection conn = null;
        Book book = new Book(request.getTitle(), request.getAuthor(), request.getQuantity());
        try {
            conn = pool.getConnection();

            bookDAO.insert(conn, book);

            conn.commit();

            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getQuantity()
            );
        } catch (Exception e) {
            if(conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }

    public List<BookResponse> findAllBooks() throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();

            List<BookResponse> books = bookDAO.findAll(conn);

            conn.commit();
            return books;
        } catch (Exception e) {
            if(conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }
}
