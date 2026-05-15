package com.das.mylibrary.repository;

import com.das.mylibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookNameContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCaseOrPublisherContainingIgnoreCase(
            String bookName,
            String author,
            String genre,
            String publisher
    );

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByBookNameContainingIgnoreCase(String bookName);

    List<Book> findByGenreContainingIgnoreCase(String genre);

    List<Book> findByPublisherContainingIgnoreCase(String publisher);

    List<Book> findByPublishedYearGreaterThanEqual(Integer publishedYear);
}