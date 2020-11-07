package com.colliers.mongodb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private long id;

    @Field("transaction_amount")
    private double transactionAmount;

    @Field("account_type")
    private AccountType accountType;

    @Transient
    private String accountTypeNumber;

    @Field("customer")
    private Customer customer;

    @Transient
    private String customerId;

    @Field("transaction_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String transactionDate;
}