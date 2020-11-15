package com.colliers.mongodb.model.mapper;

import com.colliers.mongodb.model.DTO.TransactionDTO;
import com.colliers.mongodb.model.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AccountTypeMapper.class, CustomerMapper.class})
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target="transactionDate", source="transactionDate")
    @Mapping(target="transactionId", source="id")
    @Mapping(target="transactionAmount", source="transactionAmount")
    @Mapping(target="accountTypeName", source="accountType.name")
    @Mapping(target="firstName", source="customer.firstName")
    @Mapping(target="lastName", source="customer.lastName")
    TransactionDTO transactionToDTO(Transaction transaction);

    @InheritInverseConfiguration(name = "transactionToDTO")
    Transaction DTOtoTransaction(TransactionDTO transactionDTO);
}