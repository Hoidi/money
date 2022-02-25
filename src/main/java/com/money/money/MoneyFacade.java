package com.money.money;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class MoneyFacade {

    TransactionRepository transactionRepository;
    UserRepository userRepository;
    TagRepository tagRepository;

    MoneyFacade() {
        this.transactionRepository = new InMemoryTransactionRepository();
        this.userRepository = new InMemoryUserRepository();
        this.tagRepository = new InMemoryTagRepository();

        userRepository.addUser(new User("unni"));
        userRepository.addUser(new User("tobias"));
        userRepository.addUser(new User("sponken"));


        tagRepository.addTag(new Tag("Mat", ""));
        tagRepository.addTag(new Tag("Hushåll", "hyra, möbler"));
        tagRepository.addTag(new Tag("Kläder", ""));
        tagRepository.addTag(new Tag("Annat", ""));

        transactionRepository.addTransaction(new Transaction(
                userRepository.getUser("unni"),
                userRepository.getUser("tobias"),
                200,
                Instant.now(),
                "example1", tagRepository.getTag("Mat"))
        );

        transactionRepository.addTransaction(new Transaction(
                userRepository.getUser("tobias"),
                userRepository.getUser("unni"),
                100,
                Instant.now(),
                "example2", tagRepository.getTag("Hushåll"))
        );

        for (int i = 0; i < 6; i++) {
            transactionRepository.addTransaction(new Transaction(
                    userRepository.getUser("tobias"),
                    userRepository.getUser("unni"),
                    100,
                    Instant.now(),
                    "example" + i, tagRepository.getTag("Annat"))
            );
        }

    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public void deleteTransaction(Instant instant) {
        transactionRepository.deleteTransaction(instant);
    }

    public void undeleteTransaction(Instant instant) {
        transactionRepository.undeleteTransaction(instant);
    }

    public ArrayList<Pair<String, Integer>> getDebts(String userName) {
        ArrayList<Pair<String, Integer>> debts = new ArrayList<>();
        User user = getUser(userName);

        for (User u: getAllUsers()) {
            if (!u.equals(user)) {
                debts.add(new Pair<>(u.getName(), getDebt(user, u)));
            }
        }

        return debts;
    }

    private Integer getDebt(User user, User u) {
        int debt = 0;

        for (Transaction t: getAllTransactions()) {
            if (t.getFrom().equals(user) && t.getTo().equals(u)) {
                debt -= t.getOwedSum();
            } else if (t.getFrom().equals(u) && t.getTo().equals(user)) {
                debt += t.getOwedSum();
            }
        }

        return debt;
    }

    public List<Tag> getAllTags() {
        return tagRepository.getAllTags();
    }

    public record NewTransactionDTO(String from, String to, Boolean half, int moneyAmount, String name, String tag) {

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
                        Instant.now(), tDTO.name, tagRepository.getTag(tDTO.tag))
        );
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
