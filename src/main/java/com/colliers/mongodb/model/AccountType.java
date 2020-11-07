package com.colliers.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "accountypes")
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {

    @Id
    private String id;

    @Field("account_type")
    private String accountType;
    private String name;
}