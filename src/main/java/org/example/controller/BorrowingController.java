package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.MessageResponse;
import org.example.dto.request.BorrowingRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.BorrowingDTO;
import org.example.service.BorrowingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping
    public ApiResponse<BorrowingDTO> borrowBook(@RequestBody BorrowingRequest request) throws Exception {
        log.info("Borrowing book: {}", request);
        return new ApiResponse<>(MessageResponse.BORROW_BOOK_SUCCESS, borrowingService.borrowBook(request));
    }

    @PostMapping("/return")
    public ApiResponse<BorrowingDTO> returnBook(@RequestBody BorrowingRequest request) throws Exception {
        log.info("Returning book: {}", request);
        return new ApiResponse<>(MessageResponse.RETURN_BOOK_SUCCESS, borrowingService.returnBook(request));
    }
    @GetMapping
    public ApiResponse<List<BorrowingDTO>> findAllBorrowings() throws Exception {
        log.info("Finding all borrowings");
        return new ApiResponse<>(MessageResponse.GET_ALL_BORROWING_SUCCESS, borrowingService.findAllBorrowings());
    }
    @GetMapping("/{id}")
    public ApiResponse<List<BorrowingDTO>> findAllBorrowingsByMemberId(@PathVariable int id) throws Exception {
        log.info("Finding all borrowings by member id: {}", id);
        return new ApiResponse<>(MessageResponse.GET_BORROWING_SUCCESS, borrowingService.findAllBorrowingsByMemberId(id));
    }
}
