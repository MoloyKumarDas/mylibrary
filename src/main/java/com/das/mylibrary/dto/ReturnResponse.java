package com.das.mylibrary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnResponse {

    private String message;
    private Long borrowId;
    private boolean returned;
    private Integer fineAmount;

}