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

    List<Book> findByUserId(Long userId);

    @Query("""
        SELECT b FROM Book b
        WHERE b.user.id = :userId
        AND (
            LOWER(b.bookName) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(b.genre) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(b.publisher) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)

    List<Book> searchByUser(@Param("userId") Long userId, @Param("query") String query);

    List<Book> findByUserIdAndAuthorContainingIgnoreCase(Long userId, String author);
    List<Book> findByUserIdAndBookNameContainingIgnoreCase(Long userId, String bookName);
    List<Book> findByUserIdAndGenreContainingIgnoreCase(Long userId, String genre);
    List<Book> findByUserIdAndPublisherContainingIgnoreCase(Long userId, String publisher);
    List<Book> findByUserIdAndPublishedYearGreaterThanEqual(Long userId, Integer publishedYear);

    @Lock(LockModeType.PESSIMISTIC_WRITE)                             // avoid concurrency
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") Long id);
}


