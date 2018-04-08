package org.university.innopolis.server.views;

import org.university.innopolis.server.model.Account;

public class AccountView {
    private final String login;

    public AccountView(Account account) {
        login = account.getLogin();
    }

    public String getLogin() {
        return login;
    }
}
