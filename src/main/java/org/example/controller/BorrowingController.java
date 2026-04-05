package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.BorrowingRequest;
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
    public BorrowingDTO borrowBook(@RequestBody BorrowingRequest request) throws Exception {
        log.info("Borrowing book: {}", request);
        return borrowingService.borrowBook(request);
    }

    @PostMapping("/return")
    public BorrowingDTO returnBook(@RequestBody BorrowingRequest request) throws Exception {
        log.info("Returning book: {}", request);
        return borrowingService.returnBook(request);
    }
    @GetMapping
    public List<BorrowingDTO> findAllBorrowings() throws Exception {
        log.info("Finding all borrowings");
        return borrowingService.findAllBorrowings();
    }
    @GetMapping("/{id}")
    public List<BorrowingDTO> findAllBorrowingsByMemberId(@PathVariable int id) throws Exception {
        log.info("Finding all borrowings by member id: {}", id);
        return borrowingService.findAllBorrowingsByMemberId(id);
    }
}
