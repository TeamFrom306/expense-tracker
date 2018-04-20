package org.university.innopolis.server.wrappers;

import javax.validation.constraints.NotNull;

public class BalanceWrapper {
    @NotNull
    private double balance;

    BalanceWrapper() {
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
