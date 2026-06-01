package com.das.mylibrary.service;

import com.das.mylibrary.dto.BorrowerCreateRequest;
import com.das.mylibrary.dto.BorrowerResponse;
import com.das.mylibrary.entity.Borrower;
import com.das.mylibrary.entity.User;
import com.das.mylibrary.repository.BookRepository;
import com.das.mylibrary.repository.BorrowRepository;
import com.das.mylibrary.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final UserService userService;

    public BorrowerResponse createBorrower(BorrowerCreateRequest request) {

        User user = userService.getLoggedInUser();

        if (borrowerRepository.existsByEmailAndUserId(request.getEmail(), user.getId())) {
            throw new RuntimeException("A borrower with this email already exists in your library");
        }

        if (borrowerRepository.existsByPhoneAndUserId(request.getPhone(), user.getId())) {
            throw new RuntimeException("A borrower with this phone already exists in your library");
        }

        Borrower borrower = Borrower.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .user(user)
                .build();

        Borrower saved = borrowerRepository.save(borrower);

        return BorrowerResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .address(saved.getAddress())
                .build();
    }

    public List<BorrowerResponse> getAllBorrowers() {

        User user = userService.getLoggedInUser();

        return borrowerRepository.findByUserId(user.getId())
                .stream()
                .map(b -> BorrowerResponse.builder()
                        .id(b.getId())
                        .name(b.getName())
                        .email(b.getEmail())
                        .phone(b.getPhone())
                        .address(b.getAddress())
                        .build())
                .toList();
    }
}