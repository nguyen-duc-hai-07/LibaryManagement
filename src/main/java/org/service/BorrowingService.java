package org.service;

import org.dto.response.BorrowingDTO;
import org.dto.request.BorrowingRequest;

import java.util.List;

public interface BorrowingService {
    BorrowingDTO borrowBook(BorrowingRequest request) throws Exception;

    BorrowingDTO returnBook(BorrowingRequest request) throws Exception;

    List<BorrowingDTO> findAllBorrowings() throws Exception;

    List<BorrowingDTO> findAllBorrowingsByMemberId(int memberId) throws Exception;
}
