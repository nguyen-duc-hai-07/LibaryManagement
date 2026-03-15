package service.impl;

import service.BookService;
import config.DBConnectionPool;
import dao.BookDAO;
import dto.BookResponse;
import model.Book;

import java.sql.Connection;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;
    private final DBConnectionPool pool;
    public BookServiceImpl(BookDAO bookDAO, DBConnectionPool pool) {
        this.bookDAO = bookDAO;
        this.pool = pool;
    }

    public void addBook(Book book) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();

            bookDAO.insert(conn, book);

            conn.commit();
        } catch (Exception e) {
            if(conn != null) {
                conn.rollback();
            }
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
