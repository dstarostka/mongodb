package com.colliers.mongodb.service;

import com.colliers.mongodb.exception.TransactionNotFoundException;
import com.colliers.mongodb.model.DTO.TransactionDTO;
import com.colliers.mongodb.model.Transaction;
import com.colliers.mongodb.model.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final MongoTemplate mongoTemplate;
    private final TransactionMapper MAPPER = TransactionMapper.INSTANCE;

    @Autowired
    public TransactionService(MongoTemplate mongoTemplate) {
       this.mongoTemplate = mongoTemplate;
    }

    public List<TransactionDTO> getTransactions(String accountType, String customerId) throws TransactionNotFoundException {
        final List<String> accounts = Arrays.asList(accountType.split(","));
        final List<String> customers = Arrays.asList(customerId.split(","));

        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "transactionAmount"));

        if(accountType.equals("") || accountType.equals("ALL"));
        else {
            query.addCriteria(Criteria.where("accountType.accountType").in(accounts));
        }

        if(customerId.equals("") || customerId.equals("ALL"));
        else {
            query.addCriteria(Criteria.where("customer.id").in(customers));
        }

        List<TransactionDTO> transactions = this.mongoTemplate.find(query, Transaction.class, "transactions")
                .stream().map(MAPPER::transactionToDTO).collect(Collectors.toList());

        if(transactions.isEmpty()) {
            throw new TransactionNotFoundException("Transactions not found.");
        }

        return transactions;
    }
}