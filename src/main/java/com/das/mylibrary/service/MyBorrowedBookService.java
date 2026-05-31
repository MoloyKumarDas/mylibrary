package com.das.mylibrary.service;

import com.das.mylibrary.dto.MyBorrowedBookRequest;
import com.das.mylibrary.entity.MyBorrowedBook;
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

    @Transactional
    public MyBorrowedBook addBook(
            MyBorrowedBookRequest request) {

        MyBorrowedBook book = MyBorrowedBook.builder()
                .bookTitle(request.getBookTitle())
                .ownerName(request.getOwnerName())
                .ownerPhone(request.getOwnerPhone())
                .borrowDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .notes(request.getNotes())
                .build();

        return repository.save(book);
    }

    @Transactional
    public MyBorrowedBook returnBook(Long id) {

        MyBorrowedBook book =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Record not found"));

        if (book.getReturnDate() != null) {
            throw new RuntimeException("Already returned");
        }

        book.setReturnDate(LocalDate.now());

        return repository.save(book);
    }

    @Transactional
    public MyBorrowedBook markLost(Long id) {

        MyBorrowedBook book =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Record not found"));

        if (book.getLostDate() != null) {
            throw new RuntimeException("Already marked lost");
        }

        book.setLostDate(LocalDate.now());

        return repository.save(book);
    }

    public List<MyBorrowedBook> getAll() {
        return repository.findAll();
    }

    public List<MyBorrowedBook> findByOwner(
            String ownerName) {

        return repository.findByOwnerName(ownerName);
    }
}