package com.money.money;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserRepository implements UserRepository {

    List<User> users = new ArrayList<>();

    public void addUser(User user) {
        if (user.getName().contains(" ")) {
            throw new IllegalArgumentException("User name can't contain spaces");
        }
        if (users.contains(user)) {
            throw new IllegalArgumentException("Two users can't have the same name");
        }
        // TODO: Maybe have an ID for user?

        users.add(user);
    }

    public User getUser(String name) {
        for (User u: users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        // TODO Exception?
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
