# MongoDB

Java + Spring Boot + Maven + MongoDB + OpenCSV + JUnit


What this app does?

  - loads at every app start customer, account type and transaction data from CSV files to MongoDB
  - expose one HTTP GET end point that accepts 2 params (customerId and accountType) to retrieve transactions
  - both customerId and accountType may accept multiple parameters like 1,2,5,12 and also "ALL"
  - then it retrieves data from MongoDB and returns all transactions sorted ascending by transaction amount
  - Authentication process is omitted and for simplicity user logs in via HTTP BASIC at every request
