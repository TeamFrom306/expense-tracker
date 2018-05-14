package org.university.innopolis.server.views;

public class AccountView {
    private String name;
    private double balance;
    private int accountId;

    public AccountView(String name, double balance, int accountId) {
        this.name = name;
        this.balance = balance;
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public int getAccountId() {
        return accountId;
    }
}
