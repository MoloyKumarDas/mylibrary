package com.das.mylibrary.controller;

import com.das.mylibrary.dto.BorrowerCreateRequest;
import com.das.mylibrary.dto.BorrowerResponse;
import com.das.mylibrary.service.BorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;

    @PostMapping
    public BorrowerResponse createBorrower(@Valid @RequestBody BorrowerCreateRequest request) {
        return borrowerService.createBorrower(request);
    }

    @GetMapping
    public List<BorrowerResponse> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }
}