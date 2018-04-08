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

    public void createAccount(String login) {
        accountRepository.save(new Account(login));
    }

    public AccountView getAccountById(int id) {
        Account account = accountRepository.getById(id);
        if (account == null)
            return null;
        else
            return new AccountView(account);
    }
}
