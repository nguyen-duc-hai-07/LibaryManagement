package org.service.impl;

import org.dto.request.BorrowingRequest;
import org.service.BorrowingService;
import org.config.DBConnectionPool;
import org.dao.BookDAO;
import org.dao.BorrowingDAO;
import org.dao.MemberDAO;
import org.dto.response.BorrowingDTO;
import org.model.Book;
import org.model.Borrowing;
import org.model.Member;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    private final BorrowingDAO borrowingDAO;
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    public BorrowingServiceImpl(BorrowingDAO borrowingDAO, BookDAO bookDAO, MemberDAO memberDAO) {
        this.borrowingDAO = borrowingDAO;
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
    }

    public BorrowingDTO borrowBook(BorrowingRequest request) throws Exception {
        Borrowing borrowing = new Borrowing(request.getMemberId(), request.getBookId());
        Connection conn = null;
        try {
            conn = pool.getConnection();

            Book book = bookDAO.findById(conn , request.getBookId());
            if(book == null) {
                throw new Exception("Book not found");
            }
            Member member = memberDAO.findById(conn , request.getMemberId() );
            if(member == null) {
                throw new Exception("Member not found");
            }

            if(book.getQuantity() <= 0) {
                throw new Exception("Book is out of stock");
            }

            borrowingDAO.insert(conn, borrowing);

            bookDAO.update(conn , request.getBookId() , - 1);

            conn.commit();

            return new BorrowingDTO(
                    borrowing.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    member.getName(),
                    borrowing.getBorrowDate()
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

    public BorrowingDTO returnBook(BorrowingRequest request) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();

            Borrowing borrowing = borrowingDAO.findById(conn , request.getBorrowingId());
            if(borrowing == null) {
                throw new Exception("Borrowing not found");
            }
            if(borrowing.getReturnDate() != null) {
                throw new Exception("Book has already been returned");
            }
            bookDAO.update(conn, borrowing.getBookId(), 1);
            borrowingDAO.returnBook(conn,borrowing_id);
            conn.commit();
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

    public List<BorrowingDTO> findAllBorrowings() throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<BorrowingDTO> borrowings = borrowingDAO.findAll(conn);
            conn.commit();
            return borrowings;
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
    public List<BorrowingDTO> findAllBorrowingsByMemberId(int memberId) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<BorrowingDTO> borrowings = borrowingDAO.findAllByMemberId(conn, memberId);
            conn.commit();
            return borrowings;
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
