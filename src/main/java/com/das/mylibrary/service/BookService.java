package com.das.mylibrary.service;

import com.das.mylibrary.entity.Book;
import com.das.mylibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    private void validatePublishedYear(Integer year) {
        int currentYear = Year.now().getValue();
//        if (year == null) {
//            throw new IllegalArgumentException("");
//        }

        if (year > currentYear) {
            throw new IllegalArgumentException("Enter Valid Year");
        }
//        if (year < 1000) {
//            throw new IllegalArgumentException("Published year is too old");
//        }
    }




}