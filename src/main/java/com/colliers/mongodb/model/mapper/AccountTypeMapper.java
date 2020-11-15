package com.colliers.mongodb.model.mapper;

import com.colliers.mongodb.model.AccountType;
import com.colliers.mongodb.model.DTO.AccountTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountTypeMapper {
    AccountTypeMapper INSTANCE = Mappers.getMapper(AccountTypeMapper.class);

    AccountTypeDTO accountTypeToDTO(AccountType accountType);
    AccountType DTOtoAccountType(AccountTypeDTO accountTypeDTO);
}
