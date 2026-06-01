package com.das.mylibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookName;                           // table not created like this serial --book,cverImageUrl...
                                                       // if i want column serial according to me in databse , then i have to create table manually // hybernate autometic change structure when creating
    private String coverImageUrl;

    @Column(nullable = false,length = 100)
    private String author;
    private String publisher;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private int totalCopies;
    @Column(nullable = false)
    private int availableCopies;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
//    @Min(1000)
//    @Max(2100)
    private Integer publishedYear;

    @Column(unique = true, length = 20)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}