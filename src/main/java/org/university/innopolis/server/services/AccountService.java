package org.university.innopolis.server.services;

import org.university.innopolis.server.views.AccountView;

public interface AccountService {
    AccountView adjustBalance(double newBalance, int holderId, int accountId);

    AccountView createAccount(String name, int holderId);
}
