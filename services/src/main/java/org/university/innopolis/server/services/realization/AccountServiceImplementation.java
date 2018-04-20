package org.university.innopolis.server.services.realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.realization.mappers.AccountMapper;
import org.university.innopolis.server.views.AccountView;

@Service
public class AccountServiceImplementation implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountView findAccount(String login) {
        Account account = accountRepository.getByLogin(login);
        if (account == null)
            return null;
        else
            return AccountMapper.map(account);
    }

    @Override
    public AccountView adjustBalance(double newBalance, int accountId) {
        Account account = accountRepository.getById(accountId);
        account.setBalance(newBalance);
        return AccountMapper.map(accountRepository.save(account));
    }
}