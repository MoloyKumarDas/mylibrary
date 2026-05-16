package com.das.mylibrary.repository;

import com.das.mylibrary.entity.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)                            // avoid concurrency problem
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") Long id);
}