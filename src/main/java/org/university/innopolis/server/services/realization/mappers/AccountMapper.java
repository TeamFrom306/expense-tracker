package org.university.innopolis.server.services.realization.mappers;

import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.views.AccountView;

public class AccountMapper {
    public static AccountView map(Account account) {
        return new AccountView(account.getName(), account.getBalance(), account.getId());
    }
}
