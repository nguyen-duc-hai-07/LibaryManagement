package org.example.controller;

import org.example.dto.request.BorrowingRequest;
import org.example.dto.response.BorrowingDTO;
import org.example.service.BorrowingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/borrowings")
public class BorrowingController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowingController.class);
    private final BorrowingService borrowingService;
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping
    public BorrowingDTO borrowBook(BorrowingRequest request) throws Exception {
        return borrowingService.borrowBook(request);
    }

    @PostMapping("/return")
    public BorrowingDTO returnBook(@RequestBody BorrowingRequest request) throws Exception {
        return borrowingService.returnBook(request);
    }
    @GetMapping
    public List<BorrowingDTO> findAllBorrowings() throws Exception {
        return borrowingService.findAllBorrowings();
    }
    @GetMapping("/{id}")
    public List<BorrowingDTO> findAllBorrowingsByMemberId(@PathVariable int memberId) throws Exception {
        return borrowingService.findAllBorrowingsByMemberId(memberId);
    }
}
