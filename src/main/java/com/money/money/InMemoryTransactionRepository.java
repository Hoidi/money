package com.money.money;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryTransactionRepository implements TransactionRepository{

    // Improvement: Make List binary tree or at least sorted
    private final List<Transaction> transactions = new ArrayList<>();

    private final List<Instant> transactionInstantsToDelete = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeLatestTransaction() {
        transactions.remove(transactions.size()-1);
    }

    public List<Transaction> getAllTransactionsBetweenUsers(User user1, User user2) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t: transactions) {
            if ((t.getFrom().equals(user1) && t.getTo().equals(user2)) || ((t.getFrom().equals(user2) && t.getTo().equals(user1)))) {
                result.add(t);
            }
        }

        return result;
    }

    public int owedAmount(User user1, User user2) {
        List<Transaction> transactions = getAllTransactionsBetweenUsers(user1, user2);
        int result = 0;

        for (Transaction t: transactions) {
            if (t.getFrom().equals(user2)) {
                result += t.getOwedSum();
            }
        }

        return result;
    }

    public int numberOfTransactions() {
        return transactions.size();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    @Override
    public void deleteTransaction(Instant instant) {
        transactionInstantsToDelete.add(instant);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (transactionInstantsToDelete.removeIf(t -> t.equals(instant))) {

            boolean deleted = transactions.removeIf(t -> t.getInstant().equals(instant));
            if (deleted) {
                System.out.println("Transaction deleted");
            } else {
                System.out.println("No transaction deleted as it didn't exist");
            }
        } else {
            System.out.println("Deletion undone");
        }
    }

    @Override
    public void undeleteTransaction(Instant instant) {
        transactionInstantsToDelete.remove(instant);
    }
}
