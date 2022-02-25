package com.money.money;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTransactionRepositoryTest {

    TransactionRepository repo = new InMemoryTransactionRepository();

    void setup() {
        repo = new InMemoryTransactionRepository();

        User user1 = new User("User1");
        User user2 = new User("User2");
        User user3 = new User("User3");
        User user4 = new User("User4");

        /*repo.addTransaction(new Transaction(user1, user2, 50, Instant.now(), "name1", tag));
        repo.addTransaction(new Transaction(user1, user3, 10, Instant.now(), "name2", tag));
        repo.addTransaction(new Transaction(user3, user4, 20, Instant.now(), "name3", tag));
        repo.addTransaction(new Transaction(user3, user1, 30, Instant.now(), "name4", tag));
        repo.addTransaction(new Transaction(user1, user2, 60, Instant.now(), "name5", tag));
        repo.addTransaction(new Transaction(user2, user1, 90, Instant.now(), "name6", tag));*/
    }

    @Test
    void addTransaction() {
        setup();
        assertEquals(6, repo.numberOfTransactions());

        User userAdd1 = new User("UserAdd1");
        User userAdd2 = new User("UserAdd2");

        //repo.addTransaction(new Transaction(userAdd1, userAdd2, 20, Instant.now(), "nameAdd1", tag));

        assertEquals(7, repo.numberOfTransactions());
    }

    @Test
    public void addTransactionSameUser() {
        User userAdd1 = new User("UserAdd1");

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //repo.addTransaction(new Transaction(userAdd1, userAdd1, 20, Instant.now(), "nameAddSame1", tag));
        });

        Assertions.assertEquals(IllegalArgumentException.class, thrown.getClass());
    }

    @Test
    void removeLatestTransaction() {
        setup();
        repo.removeLatestTransaction();
        assertEquals(5, repo.numberOfTransactions());
        repo.removeLatestTransaction();
        assertEquals(4, repo.numberOfTransactions());
    }

    @Test
    void getAllTransactionsBetweenUsers() {
        setup();

        User userGetAll1 = new User("User1");
        User userGetAll2 = new User("User2");
        List<Transaction> list = repo.getAllTransactionsBetweenUsers(userGetAll1, userGetAll2);
        assertEquals(3, list.size());
    }

    @Test
    void numberOfTransactions() {
        setup();
        assertEquals(6, repo.numberOfTransactions());
    }

    @Test
    void owedAmount() {

    }
}