package com.delta.commerce.repository;

import com.delta.commerce.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
}
