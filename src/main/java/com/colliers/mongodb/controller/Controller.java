package com.colliers.mongodb.controller;

import com.colliers.mongodb.model.DTO.TransactionDTO;
import com.colliers.mongodb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
class Controller {

    private TransactionService service;

    @Autowired
    Controller(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transactions")
    ResponseEntity<List<TransactionDTO>> getTransactionsByAccountTypeAndCustomerId(
        @RequestParam(value = "account_type") String accountType, @RequestParam(value = "customer_id") String customerId) {

        String regex = "^(^$)|(ALL)|(([1-9][0-9]*)((,[1-9][0-9]*)*))$";

        if(!accountType.matches(regex) || !customerId.matches(regex)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.service.getTransactions(accountType, customerId), HttpStatus.OK);
    }
}