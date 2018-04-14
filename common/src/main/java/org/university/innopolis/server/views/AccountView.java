package org.university.innopolis.server.views;

public class AccountView {
    private final String login;
    private final String token;
    private final double balance;

    public AccountView(String login, String token, double balance) {
        this.login = login;
        this.token = token;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }

    public double getBalance() {
        return balance;
    }
}
