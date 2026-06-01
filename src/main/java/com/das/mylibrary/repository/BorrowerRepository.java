package com.das.mylibrary.repository;

import com.das.mylibrary.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    List<Borrower> findByUserId(Long userId);

    boolean existsByEmailAndUserId(String email, Long userId);
    boolean existsByPhoneAndUserId(String phone, Long userId);
}