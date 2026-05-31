package com.das.mylibrary.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MyBorrowedBookRequest {

    private String bookTitle;

    private String ownerName;

    private String ownerPhone;

    private LocalDate dueDate;

    private String notes;
}