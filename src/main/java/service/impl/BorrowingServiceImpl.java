package service.impl;

import service.BorrowingService;
import config.DBConnectionPool;
import dao.BookDAO;
import dao.BorrowingDAO;
import dao.MemberDAO;
import dto.BorrowingDTO;
import model.Book;
import model.Borrowing;
import model.Member;

import java.sql.Connection;
import java.util.List;

public class BorrowingServiceImpl implements BorrowingService {
    private final BorrowingDAO borrowingDAO;
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final DBConnectionPool pool;
    public BorrowingServiceImpl(BorrowingDAO borrowingDAO, BookDAO bookDAO, MemberDAO memberDAO, DBConnectionPool pool) {
        this.borrowingDAO = borrowingDAO;
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
        this.pool = pool;
    }

    public void borrowBook(int bookId, int memberId) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();

            Book book = bookDAO.findById(conn , bookId );
            if(book == null) {
                throw new Exception("Book not found");
            }
            Member member = memberDAO.findById(conn , memberId );
            if(member == null) {
                throw new Exception("Member not found");
            }

            if(book.getQuantity() <= 0) {
                throw new Exception("Book is out of stock");
            }

            Borrowing borrowing = new Borrowing(bookId, memberId );
            borrowingDAO.insert(conn, borrowing);

            bookDAO.update(conn , bookId , - 1);

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

    public void returnBook(int borrowing_id) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();

            Borrowing borrowing = borrowingDAO.findById(conn , borrowing_id );
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
