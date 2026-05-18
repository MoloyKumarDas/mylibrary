package com.das.mylibrary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReturnResponse {

    private String message;
    private Long borrowId;
    private LocalDate ReturnDate;
    private Integer fineAmount;

}