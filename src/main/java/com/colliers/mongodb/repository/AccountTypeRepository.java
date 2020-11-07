package com.colliers.mongodb.repository;

import com.colliers.mongodb.model.AccountType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends MongoRepository<AccountType, String> {
}