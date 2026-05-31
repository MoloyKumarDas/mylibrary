package com.das.mylibrary.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyBorrowedBookResponse {

    private Long id;

    private String bookTitle;

    private String ownerName;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private LocalDate lostDate;

    private String status;
}
