package com.colliers.mongodb.service;

import com.colliers.mongodb.exception.TransactionNotFoundException;
import com.colliers.mongodb.model.DTO.TransactionDTO;
import com.colliers.mongodb.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {

    private MongoTemplate mongoTemplate;

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

        List<Transaction> transactions = this.mongoTemplate.find(query, Transaction.class, "transactions");

        if(transactions.isEmpty()) {
            throw new TransactionNotFoundException("No Transactions found.");
        }

        List<TransactionDTO> result = new ArrayList<>();

        for(var t : transactions) {
            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .transactionDate(t.getTransactionDate())
                    .transactionId(t.getId())
                    .transactionAmount(t.getTransactionAmount())
                    .accountTypeName(t.getAccountType().getName())
                    .firstName(t.getCustomer().getFirstName())
                    .lastName(t.getCustomer().getLastName())
                    .build();

            result.add(transactionDTO);
        }

        return result;
    }
}