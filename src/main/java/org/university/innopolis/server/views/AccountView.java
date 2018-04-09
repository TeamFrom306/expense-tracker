package org.university.innopolis.server.views;

import org.university.innopolis.server.model.Account;

public class AccountView {
    private final String login;
    private final String token;

    public AccountView(Account account) {
        login = account.getLogin();
        token = account.getToken();
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }
}
