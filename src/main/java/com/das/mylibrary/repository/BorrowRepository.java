package com.das.mylibrary.repository;

import com.das.mylibrary.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

//    List<Borrow> findByBorrowerId(Long borrowerId);
    List<Borrow> findByUserIdAndBorrowerId(Long userId, Long borrowerId);
//    List<Borrow> findByUserId(Long userId);
}