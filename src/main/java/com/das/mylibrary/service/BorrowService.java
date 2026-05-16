package com.das.mylibrary.service;

import com.das.mylibrary.dto.BorrowRequest;
import com.das.mylibrary.dto.ReturnRequest;
import com.das.mylibrary.entity.Book;
import com.das.mylibrary.entity.Borrow;
import com.das.mylibrary.entity.Borrower;
import com.das.mylibrary.repository.BookRepository;
import com.das.mylibrary.repository.BorrowRepository;
import com.das.mylibrary.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final BorrowerRepository borrowerRepository;

    @Transactional
    public Borrow borrowBook(BorrowRequest request) {

//        Book book = bookRepository.findById(request.getBookId())
//                .orElseThrow(() -> new RuntimeException("Book not found"));

        Book book = bookRepository.findByIdForUpdate(request.getBookId())           // handle concurrency
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        Borrow borrow = Borrow.builder()
                .book(book)
                .borrower(borrower)
                .borrowDate(LocalDate.now())
//                .dueDate(LocalDate.now().plusDays(14))
                .dueDate(request.getDueDate())
                .returned(false)
                .build();

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

    @Transactional
    public void returnBook(ReturnRequest request) {

        Borrow borrow = borrowRepository.findById(request.getBorrowId())
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (Boolean.TRUE.equals(borrow.getReturned())) {
            throw new RuntimeException("Book already returned");
        }

        Book book = borrow.getBook();

        borrow.setReturned(true);

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        borrowRepository.save(borrow);
        bookRepository.save(book);

        LocalDate today = LocalDate.now();

        if (borrow.getDueDate() != null && today.isAfter(borrow.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                    borrow.getDueDate(),
                    today
            );

            System.out.println("Late return detected. Days late: " + daysLate);
        }
    }


    public List<Borrow> getBorrowHistory(Long borrowerId) {
        return borrowRepository.findByBorrowerId(borrowerId);
    }
}
