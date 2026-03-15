package dao;

import dto.BookResponse;
import model.Book;

import java.sql.Connection;
import java.util.List;

public interface BookDAO {

    void insert(Connection conn , Book book) throws Exception;

    Book findById(Connection conn , int id) throws Exception;

    List<BookResponse> findAll(Connection conn) throws Exception;

    void update(Connection conn , int book_id ,int quantity) throws Exception;
}
