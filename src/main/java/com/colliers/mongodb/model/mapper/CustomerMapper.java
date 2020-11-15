package com.colliers.mongodb.model.mapper;

import com.colliers.mongodb.model.Customer;
import com.colliers.mongodb.model.DTO.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToDTO(Customer customer);
    Customer DTOtoCustomer(CustomerDTO customerDTO);
}