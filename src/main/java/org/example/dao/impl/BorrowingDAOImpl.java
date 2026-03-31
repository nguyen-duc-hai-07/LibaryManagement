package org.example.dao.impl;

import org.example.dao.BorrowingDAO;
import org.example.dto.response.BorrowingDTO;
import org.example.model.Borrowing;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BorrowingDAOImpl implements BorrowingDAO {
    public void insert(Connection conn , Borrowing borrowing) throws Exception {
        String sql = "INSERT INTO borrowing(member_id, book_id, borrow_date) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, borrowing.getMemberId());
            ps.setInt(2, borrowing.getBookId());
            ps.setDate(3, Date.valueOf(borrowing.getBorrowDate()));
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    borrowing.setId(rs.getInt(1));
                }
            }
        }
    }
    public Borrowing findById ( Connection conn , int id) throws Exception {
        String sql = "SELECT * FROM borrowing WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date returnDate = rs.getDate("return_date");
                    return new Borrowing(
                            rs.getInt("id"),
                            rs.getInt("member_id"),
                            rs.getInt("book_id"),
                            rs.getDate("borrow_date").toLocalDate(),
                            returnDate != null ? returnDate.toLocalDate() : null);
                }
            }
        }
        return null;
    }

    public List<BorrowingDTO> findAll(Connection conn) throws Exception {
        String sql = """
                SELECT bw.id , b.title , m.name as MemberName , bw.borrow_date
                FROM borrowing bw
                JOIN members m ON bw.member_id = m.id
                JOIN books b ON bw.book_id = b.id
                WHERE bw.return_date IS NULL
                """;
        List<BorrowingDTO> borrowings = new java.util.ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while(rs.next()) {
                borrowings.add(new BorrowingDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        null,
                        rs.getString("MemberName"),
                        rs.getDate("borrow_date").toLocalDate(),
                        null
                ));
            }
        }
        return borrowings;
    }
    public List<BorrowingDTO> findAllByMemberId(Connection conn , int memberId) throws Exception {
        String sql = """
                SELECT bw.id , b.title , b.author , bw.borrow_date
                FROM borrowing bw
                JOIN members m ON bw.member_id = m.id
                JOIN books b ON bw.book_id = b.id
                WHERE bw.member_id = ? AND bw.return_date IS NULL
                """;

        List<BorrowingDTO> borrowingDTOS = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    borrowingDTOS.add(new BorrowingDTO(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            null,
                            rs.getDate("borrow_date").toLocalDate(),
                            null
                    ));
                }
            }
        }
        return borrowingDTOS;
    }
    public void returnBook(Connection conn, int id) throws Exception {
        String sql = "UPDATE borrowing SET return_date = ? WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
