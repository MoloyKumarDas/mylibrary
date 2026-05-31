package com.das.mylibrary.controller;

import com.das.mylibrary.dto.DashboardResponse;
import com.das.mylibrary.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/currently-borrowed")
    public long getCurrentlyBorrowed() {
        return service.getCurrentlyBorrowed();
    }

    @GetMapping("/overdue")
    public long getOverdue() {
        return service.getOverdueBorrowedBooks();
    }

    @GetMapping
    public DashboardResponse getDashboard() {
        return service.getDashboard();
    }
}
