package org.university.innopolis.server.services;

import org.university.innopolis.server.views.AccountView;

public interface AccountService {
    AccountView findAccount(String login);

    AccountView adjustBalance(double newBalance, int accountId);
}
