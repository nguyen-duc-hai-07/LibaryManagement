package org.example.dao.impl;

import org.example.dao.BookDAO;
import org.example.dto.response.BookResponse;
import org.example.model.Book;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    public void insert(Connection conn , Book book) throws Exception {
        String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getQuantity());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys();) {
                if (rs.next()) {
                    book.setId(rs.getInt(1));
                }
            }
        }
    }

    public Book findById(Connection conn , int id) throws Exception {
        String sql = "SELECT * FROM books WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("quantity"));
                }
            }
        }
        return null;
    }

    public List<BookResponse> findAll(Connection conn) throws Exception {
        String sql = "SELECT * FROM books";
        List<BookResponse> books = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
            )
        {
            while(rs.next()) {
                books.add(new BookResponse(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("quantity")));
            }
        }
        return books;
    }
    public void update(Connection conn , int book_id , int quantity) throws Exception {
        String sql = "UPDATE books SET quantity = quantity + ? WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, book_id);
            ps.executeUpdate();
        }
    }
}
