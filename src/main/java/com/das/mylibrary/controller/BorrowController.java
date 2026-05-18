package com.das.mylibrary.controller;

import com.das.mylibrary.dto.*;
import com.das.mylibrary.entity.Borrow;
import com.das.mylibrary.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
                borrow.getReturnDate(),
                borrow.getFineAmount()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{borrowerId}")
    public List<Borrow> getHistory(@PathVariable Long borrowerId) {
        return borrowService.getBorrowHistory(borrowerId);
    }


    @PostMapping("/lost")
    public ResponseEntity<Borrow> markLost(@RequestBody LostBookRequest request) {

//        System.out.println("RAW REQUEST OBJECT: " + request);
//        System.out.println("BORROW ID: " + request.getBorrowId());

//        if (request.getBorrowId() == null) {
//            throw new RuntimeException("borrowId is NULL , JSON not mapped correctly");
//        }

        Borrow borrow = borrowService.markBookLost(request.getBorrowId());

        return ResponseEntity.ok(borrow);
    }


    @GetMapping("/report/{borrowerId}")
    public ResponseEntity<BorrowReportResponse> getBorrowReport(
            @PathVariable Long borrowerId) {

        BorrowReportResponse report =
                borrowService.generateBorrowReport(borrowerId);

        return ResponseEntity.ok(report);
    }

}