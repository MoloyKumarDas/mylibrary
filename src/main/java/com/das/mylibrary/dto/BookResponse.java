package com.das.mylibrary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private Long id;
    private String bookName;
    private String author;
    private String genre;
    private int totalCopies;
    private int availableCopies;
}
