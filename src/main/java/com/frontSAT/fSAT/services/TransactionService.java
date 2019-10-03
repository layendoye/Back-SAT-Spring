package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Tarifs;
import com.frontSAT.fSAT.model.Transaction;
import com.frontSAT.fSAT.repository.TarifRepository;
import com.frontSAT.fSAT.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    public Optional<Transaction> findById(int id){
        return transactionRepository.findById(id);
    }
}
