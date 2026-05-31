package com.das.mylibrary.service;

import com.das.mylibrary.dto.DashboardResponse;
import com.das.mylibrary.repository.MyBorrowedBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MyBorrowedBookRepository repository;

    // books currently you borrowed from others
    public long getCurrentlyBorrowed() {
        return repository.countByReturnDateIsNullAndLostDateIsNull();
    }

    // overdue borrowed books
    public long getOverdueBorrowedBooks() {
        return repository.countOverdueBooks();
    }

    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()
                .currentlyBorrowed(getCurrentlyBorrowed())
                .overdueBorrowed(getOverdueBorrowedBooks())
                .build();
    }
}
