package com.money.money;

import java.util.List;

public interface UserRepository {
    void addUser(User user);
    User getUser(String name);

    List<User> getAllUsers();
}
