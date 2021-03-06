package com.money.money.controller;

import com.money.money.MoneyFacade;
import com.money.money.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {

    private final MoneyFacade moneyFacade;
    private final TransactionController transactionController;
    private final UserController userController;
    private final TagController tagController;

    public SiteController(MoneyFacade moneyFacade, TransactionController transactionController, UserController userController, TagController tagController) {
        this.moneyFacade = moneyFacade;
        this.transactionController = transactionController;
        this.userController = userController;
        this.tagController = tagController;
    }

    @GetMapping
    String startPage(Model model) {
        model.addAttribute("allUsers", userController.getAllUsers());
        model.addAttribute("allTransactions", transactionController.getAllTransactions());
        model.addAttribute("allTags", tagController.getAllTags());

        return "start";
    }

    @GetMapping("/money")
    String addTransaction(Model model) {
        model.addAttribute("allUsers", userController.getAllUsers());

        return "add-transaction";
    }

    @RequestMapping("money")
    String form(MoneyFacade.NewTransactionDTO transaction) {
        moneyFacade.newTransaction(transaction);

        System.out.println("Form done");

        return "money-ok";
    }

}
