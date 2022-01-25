package com.money.money;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository {

    void addTransaction(Transaction transaction);
    void removeLatestTransaction();

    List<Transaction> getAllTransactionsBetweenUsers(User user1, User user2);
    int owedAmount(User user1, User user2);

    int numberOfTransactions();

    List<Transaction> getAllTransactions();

    void deleteTransaction(Instant instant);

    void undeleteTransaction(Instant instant);
}
