package com.das.mylibrary.service;

import com.das.mylibrary.dto.DashboardResponse;
import com.das.mylibrary.entity.User;
import com.das.mylibrary.repository.MyBorrowedBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final MyBorrowedBookRepository repository;
    private final UserService userService;

    public long getCurrentlyBorrowed() {
        User user = userService.getLoggedInUser();
        return repository.countByUserIdAndReturnDateIsNullAndLostDateIsNull(user.getId());
    }

    public long getOverdueBorrowedBooks() {
        User user = userService.getLoggedInUser();
        return repository.countOverdueBooks(user.getId());
    }

    public DashboardResponse getDashboard() {
        User user = userService.getLoggedInUser();
        return DashboardResponse.builder()
                .currentlyBorrowed(repository.countByUserIdAndReturnDateIsNullAndLostDateIsNull(user.getId()))
                .overdueBorrowed(repository.countOverdueBooks(user.getId()))
                .build();
    }
}