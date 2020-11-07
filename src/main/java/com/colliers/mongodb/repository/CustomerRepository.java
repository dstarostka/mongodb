package com.colliers.mongodb.repository;

import com.colliers.mongodb.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "customers")
public interface CustomerRepository extends MongoRepository<Customer, String> {
}