package com.das.mylibrary.controller;

import com.das.mylibrary.dto.BorrowRequest;
import com.das.mylibrary.dto.BorrowResponse;
import com.das.mylibrary.dto.ReturnRequest;
import com.das.mylibrary.dto.ReturnResponse;
import com.das.mylibrary.entity.Borrow;
import com.das.mylibrary.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowResponse> borrowBook(@Valid @RequestBody BorrowRequest request) {

        Borrow borrow = borrowService.borrowBook(request);

        BorrowResponse response = new BorrowResponse(
                borrow.getId(),
                "Book borrowed successfully",
                borrow.getDueDate()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnResponse> returnBook(@RequestBody ReturnRequest request) {

        Borrow borrow = borrowService.returnBook(request);

        ReturnResponse response = new ReturnResponse(
                "Book returned successfully",
                borrow.getId(),
//                borrow.getDueDate()
                borrow.getReturned(),
                borrow.getFineAmount()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{borrowerId}")
    public List<Borrow> getHistory(@PathVariable Long borrowerId) {
        return borrowService.getBorrowHistory(borrowerId);
    }
}