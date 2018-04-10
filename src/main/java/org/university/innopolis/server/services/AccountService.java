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

    public AccountView findAccount(String login) {
        Account account = accountRepository.getByLogin(login);
        if (account == null)
            return null;
        else
            return new AccountView(account);
    }

    public void withdrawMoney(int accountId, double amount) {
        Account account = accountRepository.getById(accountId);
        accountRepository.updateBalanceById(accountId, account.getBalance() - amount);
    }

    public void addMoney(int accountId, double amount) {
        Account account = accountRepository.getById(accountId);
        accountRepository.updateBalanceById(accountId, account.getBalance() + amount);
    }
}