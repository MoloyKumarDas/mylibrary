package com.das.mylibrary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "my_borrowed_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookTitle;

    private String ownerName;

    private String ownerPhone;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private LocalDate lostDate;

    private String notes;
}