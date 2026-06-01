package com.das.mylibrary.service;

import com.das.mylibrary.dto.MyBorrowedBookRequest;
import com.das.mylibrary.entity.MyBorrowedBook;
import com.das.mylibrary.entity.User;
import com.das.mylibrary.repository.MyBorrowedBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyBorrowedBookService {

    private final MyBorrowedBookRepository repository;
    private final UserService userService;

    @Transactional
    public MyBorrowedBook addBook(MyBorrowedBookRequest request) {

        User user = userService.getLoggedInUser();

        MyBorrowedBook book = MyBorrowedBook.builder()
                .bookTitle(request.getBookTitle())
                .ownerName(request.getOwnerName())
                .ownerPhone(request.getOwnerPhone())
                .borrowDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .notes(request.getNotes())
                .user(user)
                .build();

        return repository.save(book);
    }

    @Transactional
    public MyBorrowedBook returnBook(Long id) {

        User user = userService.getLoggedInUser();

        MyBorrowedBook book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        if (book.getReturnDate() != null) {
            throw new RuntimeException("Already returned");
        }

        book.setReturnDate(LocalDate.now());

        return repository.save(book);
    }

    @Transactional
    public MyBorrowedBook markLost(Long id) {

        User user = userService.getLoggedInUser();

        MyBorrowedBook book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        if (book.getLostDate() != null) {
            throw new RuntimeException("Already marked lost");
        }

        book.setLostDate(LocalDate.now());

        return repository.save(book);
    }

    public List<MyBorrowedBook> getAll() {

        User user = userService.getLoggedInUser();

        return repository.findByUserId(user.getId());
    }

    public List<MyBorrowedBook> findByOwner(String ownerName) {

        User user = userService.getLoggedInUser();

        return repository.findByUserIdAndOwnerName(user.getId(), ownerName);
    }
}

