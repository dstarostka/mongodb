package com.colliers.mongodb.controller;

import com.colliers.mongodb.model.AccountType;
import com.colliers.mongodb.model.Customer;
import com.colliers.mongodb.model.DTO.TransactionDTO;
import com.colliers.mongodb.model.Transaction;
import com.colliers.mongodb.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock
    private MongoTemplate mongoTemplate;

    private Controller controller;

    @BeforeEach
    void setup() {
        TransactionService service = new TransactionService(mongoTemplate);
        controller = new Controller(service);
    }

    @Test
    void test() {}

    @Test
    void getTransactionsByAccountTypeAndCustomerIdWithWIthOneAccountTypeAndOneCustomerIdTest() {
        Transaction t1 = Transaction.builder()
                .id(700)
                .transactionAmount(600.11)
                .accountType(new AccountType("1", "1", "checking account"))
                .customer(new Customer("1", "Elliot", "Alderson", 199))
                .transactionDate("2017-03-06 13:23:11")
                .build();

        when(this.mongoTemplate.find(ArgumentMatchers.any(Query.class), ArgumentMatchers.eq(Transaction.class),
                                            ArgumentMatchers.anyString())).thenReturn(Arrays.asList(t1));

        ResponseEntity<List<TransactionDTO>> result = controller.getTransactionsByAccountTypeAndCustomerId("1", "1");
        assertThat(result.getBody().get(0).getAccountTypeName()).isEqualTo("checking account");
        assertThat(result.getBody().get(0).getFirstName()).isEqualTo("Elliot");
    }

    @Test
    void getTransactionsByAccountTypeAndCustomerIdWithOneCustomerIdAndEmptyAccountTypeTest() {
        Transaction t1 = Transaction.builder()
                .id(700)
                .transactionAmount(600.11)
                .accountType(new AccountType("1", "1", "checking account"))
                .customer(new Customer("1", "Elliot", "Alderson", 199))
                .transactionDate("2017-03-06 13:23:11")
                .build();

        Transaction t2 = Transaction.builder()
                .id(723)
                .transactionAmount(524.32)
                .accountType(new AccountType("2", "2", "saving account"))
                .customer(new Customer("1", "Elliot", "Alderson", 199))
                .transactionDate("2013-08-04 23:57:38")
                .build();

        when(mongoTemplate.find(ArgumentMatchers.any(Query.class), ArgumentMatchers.eq(Transaction.class),
                ArgumentMatchers.anyString())).thenReturn(Arrays.asList(t1, t2));

        ResponseEntity<List<TransactionDTO>> result = controller.getTransactionsByAccountTypeAndCustomerId("", "1");
        assertThat(result.getBody().get(0).getAccountTypeName()).isEqualTo("checking account");
        assertThat(result.getBody().get(0).getFirstName()).isEqualTo("Elliot");

        assertThat(result.getBody().get(1).getAccountTypeName()).isEqualTo("saving account");
        assertThat(result.getBody().get(1).getFirstName()).isEqualTo("Elliot");
    }

    @Test
    void getTransactionsByAccountTypeAndCustomerIdWithEmptyAccountTypeAndALLCustomerIdTest() {
        Transaction t1 = Transaction.builder()
                .id(700)
                .transactionAmount(600.11)
                .accountType(new AccountType("1", "1", "checking account"))
                .customer(new Customer("1", "Elliot", "Alderson", 199))
                .transactionDate("2017-03-06 13:23:11")
                .build();

        Transaction t2 = Transaction.builder()
                .id(723)
                .transactionAmount(524.32)
                .accountType(new AccountType("2", "2", "saving account"))
                .customer(new Customer("1", "Elliot", "Alderson", 199))
                .transactionDate("2013-08-04 23:57:38")
                .build();

        Transaction t3 = Transaction.builder()
                .id(200)
                .transactionAmount(500)
                .accountType(new AccountType("1", "1", "checking account"))
                .customer(new Customer("2", "Tyrell", "Wellick", 10000))
                .transactionDate("2020-10-07 23:59:59")
                .build();

        Transaction t4 = Transaction.builder()
                .id(540)
                .transactionAmount(1000)
                .accountType(new AccountType("2", "2", "saving account"))
                .customer(new Customer("2", "Tyrell", "Wellick", 10000))
                .transactionDate("2018-02-01 12:05:18")
                .build();

        when(mongoTemplate.find(ArgumentMatchers.any(Query.class), ArgumentMatchers.eq(Transaction.class),
                ArgumentMatchers.anyString())).thenReturn(Arrays.asList(t1, t2, t3, t4));

        ResponseEntity<List<TransactionDTO>> result = controller.getTransactionsByAccountTypeAndCustomerId("", "ALL");
        assertThat(result.getBody().get(0).getAccountTypeName()).isEqualTo("checking account");
        assertThat(result.getBody().get(0).getFirstName()).isEqualTo("Elliot");

        assertThat(result.getBody().get(1).getAccountTypeName()).isEqualTo("saving account");
        assertThat(result.getBody().get(1).getFirstName()).isEqualTo("Elliot");

        assertThat(result.getBody().get(2).getAccountTypeName()).isEqualTo("checking account");
        assertThat(result.getBody().get(2).getFirstName()).isEqualTo("Tyrell");

        assertThat(result.getBody().get(3).getAccountTypeName()).isEqualTo("saving account");
        assertThat(result.getBody().get(3).getFirstName()).isEqualTo("Tyrell");
    }
}