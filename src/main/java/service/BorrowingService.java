package service;

import dto.BorrowingDTO;
import java.util.List;

public interface BorrowingService {
    void borrowBook(int bookId, int memberId) throws Exception;

    void returnBook(int borrowing_id) throws Exception;

    List<BorrowingDTO> findAllBorrowings() throws Exception;

    List<BorrowingDTO> findAllBorrowingsByMemberId(int memberId) throws Exception;
}
