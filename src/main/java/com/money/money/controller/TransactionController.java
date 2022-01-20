package com.money.money.controller;

import com.money.money.MoneyFacade;
import com.money.money.Transaction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final MoneyFacade moneyFacade;

    TransactionController(MoneyFacade moneyFacade) {
        this.moneyFacade = moneyFacade;
    }

    @GetMapping("/trans/{name}")
    String owedSum(@PathVariable("name") String name, Model model) {
        model.addAttribute("something", "this is my attribute");

        int owedSum = moneyFacade.howMuchDoUserOwe(moneyFacade.getUser(name));
        return String.valueOf(owedSum);
    }

    @GetMapping("/transall")
    String getUser() {
        StringBuilder sb = new StringBuilder();

        sb.append("::Transactions::<br>\n");

        for (Transaction t: moneyFacade.getAllTransactions()) {
            sb.append("Transaction: <br>");
            sb.append("\t&nbsp;&nbsp;&nbsp;&nbsp; From:    " + t.getFrom().getName() + " <br>\n");
            sb.append("\t&nbsp;&nbsp;&nbsp;&nbsp; To:      " + t.getTo().getName() + "<br>\n");
            sb.append("\t&nbsp;&nbsp;&nbsp;&nbsp; Owed:    " + t.getOwedSum() + "<br>\n");
            sb.append("\t&nbsp;&nbsp;&nbsp;&nbsp; Instant: " + t.getInstant().toString() + "<br>\n");
            sb.append("\t&nbsp;&nbsp;&nbsp;&nbsp; Name:    " + t.getName() + "<br>\n");
            sb.append("<br>\n");
        }

        return sb.toString();
    }


    public List<Transaction> getAllTransactions() {
        return moneyFacade.getAllTransactions();
    }
}
