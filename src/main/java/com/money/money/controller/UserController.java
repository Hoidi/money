package com.money.money.controller;

import com.money.money.MoneyFacade;
import com.money.money.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final MoneyFacade moneyFacade;

    UserController(MoneyFacade moneyFacade) {
        this.moneyFacade = moneyFacade;
    }

    @GetMapping("/user/{name}")
    String getUser(@PathVariable("name") String name) {
        User user = moneyFacade.getUser(name);
        if (user == null) {
            return "User does not exist";
        }

        return user.getName();
    }

    @GetMapping("/adduser/{name}")
    String addUser(@PathVariable("name") String name) {
        String result = moneyFacade.addUser(name);

        return result;
    }


    public List<User> getAllUsers() {
        return moneyFacade.getAllUsers();
    }
}
