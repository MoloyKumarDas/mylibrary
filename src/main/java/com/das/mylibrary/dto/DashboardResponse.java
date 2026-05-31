package com.das.mylibrary.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardResponse {

    private long currentlyBorrowed;
    private long overdueBorrowed;
}