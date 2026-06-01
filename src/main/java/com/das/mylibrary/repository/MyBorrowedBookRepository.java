package com.das.mylibrary.repository;

import com.das.mylibrary.entity.MyBorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyBorrowedBookRepository
        extends JpaRepository<MyBorrowedBook, Long> {

    List<MyBorrowedBook> findByUserId(Long userId);

    List<MyBorrowedBook> findByUserIdAndOwnerName(Long userId, String ownerName);
    //List<MyBorrowedBook> findByOwnerName(String ownerName);


    // counts only this user's books
    long countByUserIdAndReturnDateIsNullAndLostDateIsNull(Long userId);

    // counts only this user's overdue books
    @Query("""
    SELECT COUNT(b)
    FROM MyBorrowedBook b
    WHERE b.user.id = :userId
    AND b.dueDate < CURRENT_DATE
    AND b.returnDate IS NULL
    AND b.lostDate IS NULL
""")

    long countOverdueBooks(@Param("userId") Long userId);


    // currently borrowed (not returned + not lost)
//    long countByReturnDateIsNullAndLostDateIsNull();
//
//    // overdue books
//    @Query("""
//        SELECT COUNT(b)
//        FROM MyBorrowedBook b
//        WHERE b.dueDate < CURRENT_DATE
//        AND b.returnDate IS NULL
//        AND b.lostDate IS NULL
//    """)
//    long countOverdueBooks();
}
