package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.views.AccountView;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountView createAccount(String login, String password) throws DuplicatedUserException {
        if (containsLogin(login))
            throw new DuplicatedUserException(login);

        Account res = accountRepository.save(new Account(login, password));
        return new AccountView(res);
    }

    private boolean containsLogin(String login) {
        return accountRepository.getByLogin(login) != null;
    }

    public AccountView findAccount(String login) {
        Account account = accountRepository.getByLogin(login);
        if (account == null)
            return null;
        else
            return new AccountView(account);
    }

    public boolean isAuthorized(int accountId, String login) {
        Account account = accountRepository.getByIdAndLogin(accountId, login);
        return account != null;
    }
}
