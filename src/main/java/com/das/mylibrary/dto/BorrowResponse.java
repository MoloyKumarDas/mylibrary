package com.das.mylibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BorrowResponse {
    private Long borrowId;
    private String message;
    private LocalDate dueDate;
}
