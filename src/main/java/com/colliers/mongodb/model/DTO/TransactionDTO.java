package com.colliers.mongodb.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"transaction_date", "transaction_id", "transaction_amount", "account_type", "first_name", "last_name"})
public class TransactionDTO {

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("transaction_id")
    private long transactionId;

    @JsonProperty("transaction_amount")
    private double transactionAmount;

    @JsonProperty("account_type")
    private String accountTypeName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
}