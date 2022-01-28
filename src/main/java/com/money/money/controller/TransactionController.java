package com.money.money.controller;

import com.money.money.MoneyFacade;
import com.money.money.Transaction;
import org.javatuples.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    private final MoneyFacade moneyFacade;

    TransactionController(MoneyFacade moneyFacade) {
        this.moneyFacade = moneyFacade;
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

    @DeleteMapping("/deletetransaction/{transInstans}")
    void deleteTransaction(@PathVariable("transInstans") String transInstans) {
        Instant instant = Instant.parse(transInstans);

        moneyFacade.deleteTransaction(instant);
    }

    @DeleteMapping("/undeletetransaction/{transInstans}")
    void undeleteTransaction(@PathVariable("transInstans") String transInstans) {
        Instant instant = Instant.parse(transInstans);

        moneyFacade.undeleteTransaction(instant);
    }

    @GetMapping("/getdebt/{user}")
    String getDept(@PathVariable("user") String user) {
        ArrayList<Pair<String, Integer>> debts =  moneyFacade.getDebts(user);

        StringBuilder result = new StringBuilder();

        result.append("{\n");

        for (int i = 0; i < debts.size() - 1; i++) {
            Pair p = debts.get(i);

            result.append("\t \"userDebt" + i + "\": {\n");
            result.append("\t\t\"name\": \"" + p.getValue0() + "\"");
            result.append(",\n");
            result.append("\t\t\"debt\": \"" + p.getValue1() + "\"\n");
            result.append("\t}");

            result.append(" , \n");
        }

        // add last element
        result.append("\t \"userDebt" + (debts.size()-1) + "\": {\n");
        result.append("\t\t\"name\": \"" + debts.get(debts.size()-1).getValue0() + "\"");
        result.append(",\n");
        result.append("\t\t\"debt\": \"" + debts.get(debts.size()-1).getValue1() + "\"\n");
        result.append("\t}\n");

        result.append("}");

        return result.toString();
    }

    public List<Transaction> getAllTransactions() {
        return moneyFacade.getAllTransactions();
    }
}
