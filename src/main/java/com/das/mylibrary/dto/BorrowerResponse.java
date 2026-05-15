package com.das.mylibrary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowerResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}