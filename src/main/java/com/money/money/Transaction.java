package com.money.money;

import java.time.Instant;
import java.time.ZoneId;

public class Transaction {

    private final User from;
    private final User to;
    private final int owedSum;
    private final Instant instant;
    private final String name;

    public Transaction(User from, User to, int owedSum, Instant instant, String name) {

        if (from.equals(to)) {
            throw new IllegalArgumentException("Transaction can't be to and from the same user.");
        }

        this.from = from;
        this.to = to;
        this.owedSum = owedSum;
        this.instant = instant;
        this.name = name;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public int getOwedSum() {
        return owedSum;
    }

    public Instant getInstant() {
        return instant;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        StringBuilder date = new StringBuilder();
        date.append(instant.atZone(ZoneId.systemDefault()).toLocalDate()).append(" ");
        date.append(instant.atZone(ZoneId.systemDefault()).getHour()).append(":");
        if (instant.atZone(ZoneId.systemDefault()).getMinute() <= 9) {
            date.append("0");
        }
        date.append(instant.atZone(ZoneId.systemDefault()).getMinute());

        return date.toString();
    }
}
