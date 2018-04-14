package org.university.innopolis.server.services_realization.mappers;

import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.views.AccountView;

public class AccountMapper {
    private AccountMapper() {}

    public static AccountView map(Account account) {
        return new AccountView(
                account.getLogin(),
                account.getToken(),
                account.getBalance());
    }
}
