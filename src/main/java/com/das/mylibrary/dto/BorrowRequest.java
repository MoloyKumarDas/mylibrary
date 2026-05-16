package com.das.mylibrary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowRequest {

    private Long bookId;

//    private String borrowerName;
//    private String borrowerEmail;
//    private String borrowerPhone;
    private Long borrowerId;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

}