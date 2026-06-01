package com.das.mylibrary.service;

import com.das.mylibrary.dto.*;
import com.das.mylibrary.entity.Book;
import com.das.mylibrary.entity.Borrow;
import com.das.mylibrary.entity.Borrower;
import com.das.mylibrary.entity.User;
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
    private final UserService userService;


    @Transactional
    public Borrow borrowBook(BorrowRequest request) {

        User user = userService.getLoggedInUser();

//        Book book = bookRepository.findById(request.getBookId())
//                .orElseThrow(() -> new RuntimeException("Book not found"));

        Book book = bookRepository.findByIdForUpdate(request.getBookId())           // handle concurrency
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        if (!borrower.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        Borrow borrow = Borrow.builder()
                .book(book)
                .borrower(borrower)
                .borrowDate(LocalDate.now())
//                .dueDate(LocalDate.now().plusDays(14))
                .dueDate(request.getDueDate())
//                .returned(false)
                .user(user)
                .build();

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

    @Transactional
    public Borrow returnBook(ReturnRequest request) {

        User user = userService.getLoggedInUser();

        Borrow borrow = borrowRepository.findById(request.getBorrowId())
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (!borrow.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

//        if (Boolean.TRUE.equals(borrow.getReturned())) {
        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        Book book = borrow.getBook();

//        borrow.setReturned(true);
        borrow.setReturnDate(LocalDate.now());

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        LocalDate today = LocalDate.now();

        if (borrow.getDueDate() != null && today.isAfter(borrow.getDueDate())) {

            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                    borrow.getDueDate(),
                    today
            );

            int fine = (int) daysLate * 5;

            borrow.setFineAmount(fine);

            System.out.println("Late return detected. Days late: " + daysLate);
        }

        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

//    public List<Borrow> getBorrowHistory(Long borrowerId) {
//        User user = userService.getLoggedInUser();
//        return borrowRepository.findByUserId(user.getId());
//    }
public List<Borrow> getBorrowHistory(Long borrowerId) {
    User user = userService.getLoggedInUser();

    Borrower borrower = borrowerRepository.findById(borrowerId)
            .orElseThrow(() -> new RuntimeException("Borrower not found"));

    if (!borrower.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("Unauthorized access");
    }

    return borrowRepository.findByUserIdAndBorrowerId(user.getId(), borrowerId);
}



public BorrowReportResponse generateBorrowReport(Long borrowerId) {

    User user = userService.getLoggedInUser();

    Borrower borrower = borrowerRepository.findById(borrowerId)
            .orElseThrow(() -> new RuntimeException("Borrower not found"));

    if (!borrower.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("Unauthorized access");
    }

    List<Borrow> borrows = borrowRepository.findByUserIdAndBorrowerId(user.getId(), borrowerId);

    if (borrows.isEmpty()) {
        throw new RuntimeException("No borrow history found");
    }

    int lowestDelay = Integer.MAX_VALUE;
    int highestDelay = 0;
    int totalDelay = 0;
    int count = 0;
    int lostBooks = 0;

    LocalDate today = LocalDate.now();

    for (Borrow borrow : borrows) {

        int delay = 0;

        if (borrow.getDueDate() != null) {

            LocalDate endDate;

            if (borrow.getReturnDate() != null) {
                endDate = borrow.getReturnDate();
            } else if (borrow.getLostDate() != null) {
                endDate = borrow.getLostDate();
            } else {
                endDate = today;
            }

            if (endDate.isAfter(borrow.getDueDate())) {
                delay = (int) java.time.temporal.ChronoUnit.DAYS.between(
                        borrow.getDueDate(),
                        endDate
                );
            }
        }

        lowestDelay = Math.min(lowestDelay, delay);
        highestDelay = Math.max(highestDelay, delay);

        totalDelay += delay;
        count++;

        if (borrow.getLostDate() != null) {
            lostBooks++;
        }
    }

    double averageDelay = count == 0 ? 0 : (double) totalDelay / count;

    String category;

    if (averageDelay <= 1) {
        category = "Excellent Borrower";
    } else if (averageDelay <= 3) {
        category = "Good Borrower";
    } else if (averageDelay <= 7) {
        category = "Average Borrower";
    } else {
        category = "Risky Borrower";
    }

    if (lowestDelay == Integer.MAX_VALUE) {
        lowestDelay = 0;
    }

    return BorrowReportResponse.builder()
            .lowestDelay(lowestDelay)
            .highestDelay(highestDelay)
            .averageDelay(averageDelay)
            .delayCategory(category)
            .lostBooks(lostBooks)
            .build();
}


    @Transactional
    public Borrow markBookLost(Long borrowId) {

        User user = userService.getLoggedInUser();

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (!borrow.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }


//        if (Boolean.TRUE.equals(borrow.getReturned())) {
        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        if (borrow.getLostDate() != null) {
            throw new RuntimeException("Book already marked as lost");
        }

        Book book = borrow.getBook();

//        borrow.setLost(true);
//        borrow.setReturned(true);
        borrow.setLostDate(LocalDate.now());
        borrow.setFineAmount(500);

//        book.setAvailableCopies(book.getAvailableCopies() - 1);
        int copies = book.getAvailableCopies();
        book.setAvailableCopies(Math.max(0, copies - 1));

        borrowRepository.save(borrow);
        bookRepository.save(book);

        return borrow;
    }


}
