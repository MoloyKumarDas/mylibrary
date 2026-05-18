package com.das.mylibrary.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
        "lowestDelay",
        "highestDelay",
        "averageDelay",
        "delayCategory",
        "lostBooks"
})
public class BorrowReportResponse {
    private int lowestDelay;
    private int highestDelay;
    private double averageDelay;
    private String delayCategory;
    private int lostBooks;

}
