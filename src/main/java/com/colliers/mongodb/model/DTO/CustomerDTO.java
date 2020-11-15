package com.colliers.mongodb.model.DTO;

import lombok.Data;

@Data
public class CustomerDTO {

    private String id;
    private String firstName;
    private String lastName;
    private double lastLoginBalance;
}