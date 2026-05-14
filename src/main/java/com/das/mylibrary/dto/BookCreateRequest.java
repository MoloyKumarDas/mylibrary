package com.das.mylibrary.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookCreateRequest {

    @NotBlank
    private String bookName;

    private String coverImageUrl;

    @NotBlank
    private String author;

    private String publisher;

    @NotBlank
    private String genre;

    @NotBlank
    private String language;

    @Min(1)
    private int totalCopies;

    private String description;

    private Integer publishedYear;

    private String isbn;
}
