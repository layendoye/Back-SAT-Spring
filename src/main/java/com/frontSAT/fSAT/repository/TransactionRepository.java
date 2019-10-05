package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Tarifs;
import com.frontSAT.fSAT.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findTransactionByCode(String code);
}