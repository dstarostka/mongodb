package com.colliers.mongodb.repository;

import com.colliers.mongodb.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "transactions")
public interface TransactionRepository extends MongoRepository<Transaction, Long> {
}