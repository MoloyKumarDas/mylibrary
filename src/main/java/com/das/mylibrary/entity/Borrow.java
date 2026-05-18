package com.das.mylibrary.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Borrowed Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Borrower info
//    private String borrowerName;
//
//    private String borrowerEmail;
//
//    private String borrowerPhone;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    // Dates
    private LocalDate borrowDate;

    private LocalDate dueDate;

    // Returned or not
    // private Boolean returned = false;
    private LocalDate returnDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Integer fineAmount = 0;

//    private Boolean lost ;
    private LocalDate lostDate;

}