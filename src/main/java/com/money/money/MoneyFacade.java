package com.money.money;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class MoneyFacade {

    TransactionRepository transactionRepository;
    UserRepository userRepository;

    MoneyFacade() {
        this.transactionRepository = new InMemoryTransactionRepository();
        this.userRepository = new InMemoryUserRepository();

        userRepository.addUser(new User("unni"));
        userRepository.addUser(new User("tobias"));

        transactionRepository.addTransaction(new Transaction(
                userRepository.getUser("unni"),
                userRepository.getUser("tobias"),
                200,
                Instant.now(),
                "example1")
        );

        transactionRepository.addTransaction(new Transaction(
                userRepository.getUser("tobias"),
                userRepository.getUser("unni"),
                100,
                Instant.now(),
                "example2")
        );

        for (int i = 0; i < 6; i++) {
            transactionRepository.addTransaction(new Transaction(
                    userRepository.getUser("tobias"),
                    userRepository.getUser("unni"),
                    100,
                    Instant.now(),
                    "example" + i)
            );
        }

        // "tobias -100-> unni"
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public record NewTransactionDTO(String from, String to, Boolean half, int moneyAmount, String name) {

    }

    public void newTransaction(NewTransactionDTO tDTO) {
        int owedSum = tDTO.moneyAmount;
        if (tDTO.half != null && tDTO.half) { // if it's null it means that the switch is not checked
            owedSum /= 2;
        }

        transactionRepository.addTransaction(
                new Transaction(
                        userRepository.getUser(tDTO.from),
                        userRepository.getUser(tDTO.to),
                        owedSum,
                        Instant.now(), tDTO.name)
        );
    }

    public void removeAllDept(User from, User to) {
        int amount = transactionRepository.owedAmount(from, to);
        removeSomeDept(from, to, amount);
    }

    public void removeSomeDept(User from, User to, int amount) {
        Transaction transaction = new Transaction(
                from, to,
                amount,
                Instant.now(),
                "Clear " + amount + " dept");

        transactionRepository.addTransaction(transaction);
    }

    public int howMuchDoUserOwe(User user) {
        // TODO Calculate how much user owes to all other users
        return 0;
    }

    public User getUser(String name) {
        return userRepository.getUser(name);
    }

    public String addUser(String name) {
        try {
            userRepository.addUser(new User(name));
            return "Success";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
