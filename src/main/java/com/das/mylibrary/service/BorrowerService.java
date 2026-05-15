package com.das.mylibrary.service;

import com.das.mylibrary.dto.BorrowerCreateRequest;
import com.das.mylibrary.dto.BorrowerResponse;
import com.das.mylibrary.entity.Borrower;
import com.das.mylibrary.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerResponse createBorrower(BorrowerCreateRequest request) {

        Borrower borrower = Borrower.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
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

        return borrowerRepository.findAll()
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