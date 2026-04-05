package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.BorrowingRequest;
import org.example.exception.BusinessException;
import org.example.exception.NotFoundException;
import org.example.service.BorrowingService;
import org.example.config.DBConnectionPool;
import org.example.dao.BookDAO;
import org.example.dao.BorrowingDAO;
import org.example.dao.MemberDAO;
import org.example.dto.response.BorrowingDTO;
import org.example.model.Book;
import org.example.model.Borrowing;
import org.example.model.Member;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@Slf4j
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
        log.info("Borrowing book: {}", borrowing);
        Connection conn = null;
        try {
            conn = pool.getConnection();
            Book book = bookDAO.findById(conn , request.getBookId());
            if(book == null) {
                log.error("Book not found: id = {}", request.getBookId());
                throw new NotFoundException("Book not found: id = " + request.getBookId());
            }
            Member member = memberDAO.findById(conn , request.getMemberId() );
            if(member == null) {
                log.error("Member not found: id = {}", request.getMemberId());
                throw new NotFoundException("Member not found: id = " + request.getMemberId() );
            }

            if(book.getQuantity() <= 0) {
                log.error("Book is out of stock: id = {}", request.getBookId());
                throw new BusinessException("Book is out of stock");
            }

            borrowingDAO.insert(conn, borrowing);

            bookDAO.update(conn , request.getBookId() , - 1);

            conn.commit();
            log.info("Book borrowed successfully");
            return new BorrowingDTO(
                    borrowing.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    member.getName(),
                    borrowing.getBorrowDate(),
                    null
            );
        } catch (Exception e) {
            log.error("Error borrowing book: {}", e.getMessage(), e);
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
        log.info("Returning book: {}", request);
        Connection conn = null;
        try {
            conn = pool.getConnection();

            Borrowing borrowing = borrowingDAO.findById(conn , request.getBorrowingId());
            if(borrowing == null) {
                log.error("Borrowing not found: id = {}", request.getBorrowingId());
                throw new NotFoundException("Borrowing not found: id = " + request.getBorrowingId());
            }
            if(borrowing.getReturnDate() != null) {
                log.error("Book has already been returned");
                throw new BusinessException("Book has already been returned");
            }
            bookDAO.update(conn, borrowing.getBookId(), 1);
            borrowingDAO.returnBook(conn,request.getBorrowingId());
            conn.commit();
            log.info("Book returned successfully");
            return new BorrowingDTO(
                    borrowing.getId(),
                    null,
                    null,
                    null,
                    borrowing.getBorrowDate(),
                    LocalDate.now()
            );
        } catch (Exception e) {
            log.error("Error returning book: {}", e.getMessage(), e);
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
        log.info("Finding all borrowings");
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<BorrowingDTO> borrowings = borrowingDAO.findAll(conn);
            conn.commit();
            log.info("Found {} borrowings", borrowings.size());
            return borrowings;
        } catch (Exception e) {
            log.error("Error finding borrowings: {}", e.getMessage(), e);
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
        log.info("Finding all borrowings by member id: {}", memberId);
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<BorrowingDTO> borrowings = borrowingDAO.findAllByMemberId(conn, memberId);
            conn.commit();
            return borrowings;
        } catch (Exception e) {
            log.error("Error finding borrowings: {}", e.getMessage(), e);
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
