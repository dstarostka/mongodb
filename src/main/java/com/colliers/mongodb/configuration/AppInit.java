package com.colliers.mongodb.configuration;

import com.colliers.mongodb.model.AccountType;
import com.colliers.mongodb.model.Customer;
import com.colliers.mongodb.model.Transaction;
import com.colliers.mongodb.repository.AccountTypeRepository;
import com.colliers.mongodb.repository.CustomerRepository;
import com.colliers.mongodb.repository.TransactionRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class AppInit implements CommandLineRunner {

    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private AccountTypeRepository accountTypeRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public AppInit(CustomerRepository customerRepository, TransactionRepository transactionRepository,
                   AccountTypeRepository accountTypeRepository, MongoTemplate mongoTemplate) {

        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.dropCollection("customers");
        mongoTemplate.dropCollection("transactions");
        mongoTemplate.dropCollection("accountypes");
        mongoTemplate.createCollection("customers");
        mongoTemplate.createCollection("transactions");
        mongoTemplate.createCollection("accountypes");

        File customersCSV = new File("src/main/resources/static/customers.csv");
        File transactionsCSV = new File("src/main/resources/static/transactions.csv");
        File accountypesCSV = new File("src/main/resources/static/accountypes.csv");

        Map<String, Customer> customers = parseCustomers(customersCSV);
        customerRepository.saveAll(customers.values());

        Map<String, AccountType> accountTypes = parseAccountTypes(accountypesCSV);
        accountTypeRepository.saveAll(accountTypes.values());

        List<Transaction> transactions = parseTransactions(transactionsCSV);
        for(var t : transactions) {
            t.setAccountType(accountTypes.get(t.getAccountTypeNumber()));
            t.setCustomer(customers.get(t.getCustomerId()));
        }

        transactionRepository.saveAll(transactions);
    }

    private List<Transaction> parseTransactions(File file) throws Exception {
        HeaderColumnNameTranslateMappingStrategy<Transaction> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(Transaction.class);

        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("transaction_id", "id");
        columnMapping.put("transaction_amount", "transactionAmount");
        columnMapping.put("account_type", "accountTypeNumber");
        columnMapping.put("customer_id", "customerId");
        columnMapping.put("transaction_date", "transactionDate");

        strategy.setColumnMapping(columnMapping);

        CSVReader reader = new CSVReader(new FileReader(file.getPath()));
        CsvToBean<Transaction> csvToBean = new CsvToBeanBuilder<Transaction>(reader)
                .withMappingStrategy(strategy)
                .build();

        return csvToBean.parse();
    }

    private Map<String, Customer> parseCustomers(File file) throws IOException {
        HeaderColumnNameTranslateMappingStrategy<Customer> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(Customer.class);

        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("id", "id");
        columnMapping.put("first_name", "firstName");
        columnMapping.put("last_name", "lastName");
        columnMapping.put("last_login_balance", "lastLoginBalance");

        strategy.setColumnMapping(columnMapping);

        CSVReader reader = new CSVReader(new FileReader(file.getPath()));
        CsvToBean<Customer> csvToBean = new CsvToBeanBuilder<Customer>(reader)
                .withMappingStrategy(strategy)
                .build();

        return csvToBean.parse().stream().collect(Collectors.toMap(Customer::getId, c -> c));
    }

    private Map<String, AccountType> parseAccountTypes(File file) throws IOException {
        HeaderColumnNameTranslateMappingStrategy<AccountType> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(AccountType.class);


        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("account_type", "accountType");
        columnMapping.put("name", "name");

        strategy.setColumnMapping(columnMapping);

        CSVReader reader = new CSVReader(new FileReader(file.getPath()));
        CsvToBean<AccountType> csvToBean = new CsvToBeanBuilder<AccountType>(reader)
                .withMappingStrategy(strategy)
                .build();

        return csvToBean.parse().stream().collect(Collectors.toMap(AccountType::getAccountType, a -> a));
    }
}