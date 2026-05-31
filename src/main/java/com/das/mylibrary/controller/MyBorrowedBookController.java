package com.das.mylibrary.controller;

import com.das.mylibrary.dto.MyBorrowedBookRequest;
import com.das.mylibrary.entity.MyBorrowedBook;
import com.das.mylibrary.service.MyBorrowedBookService;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-borrowed-books")
@RequiredArgsConstructor
public class MyBorrowedBookController {

    private final MyBorrowedBookService service;

    @PostMapping
    public MyBorrowedBook add(
            @RequestBody MyBorrowedBookRequest request) {

        return service.addBook(request);
    }

    @GetMapping
    public List<MyBorrowedBook> getAll() {
        return service.getAll();
    }

    @PostMapping("/{id}/return")
    public MyBorrowedBook returnBook(
            @PathVariable Long id) {

        return service.returnBook(id);
    }

    @PostMapping("/{id}/lost")
    public MyBorrowedBook lostBook(
            @PathVariable Long id) {

        return service.markLost(id);
    }

    @GetMapping("/owner/{ownerName}")
    public List<MyBorrowedBook> byOwner(
            @PathVariable String ownerName) {

        return service.findByOwner(ownerName);
    }
}