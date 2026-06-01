package com.das.mylibrary.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
        "id",
        "bookName",
        "author",
        "publisher",
        "coverImageUrl",
        "genre",
        "availableCopies",
        "totalCopies",
        "description",
        "language",
        "publishedYear",
        "isbn"
})
public class BookResponse {

    private Long id;
    private String bookName;
    private String coverImageUrl;
    private String author;
    private String publisher;
    private String genre;
    private String language;
    private int totalCopies;
    private int availableCopies;
    private String description;
    private Integer publishedYear;
    private String isbn;
}
