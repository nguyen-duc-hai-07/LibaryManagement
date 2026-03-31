package org.example.dao;

import org.example.dto.response.BorrowingDTO;
import org.example.model.Borrowing;

import java.sql.Connection;
import java.util.List;

public interface BorrowingDAO {

    void insert(Connection conn , Borrowing borrowing) throws Exception;

    Borrowing findById ( Connection conn , int id) throws Exception;

    List<BorrowingDTO> findAll(Connection conn) throws Exception;

    List<BorrowingDTO> findAllByMemberId(Connection conn , int memberId) throws Exception;

    void returnBook(Connection conn, int id) throws Exception;
}
