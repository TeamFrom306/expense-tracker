package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.views.AccountView;

import java.util.Objects;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private TokenService tokenService;

    @Autowired
    public AccountService(AccountRepository accountRepository, TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
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

    public AccountView authAccount(String login, String password) throws BadCredentialsException {
        Account account = accountRepository.getByLogin(login);
        if (account == null || !checkPasswords(password, account))
            throw new BadCredentialsException();
        storeToken(account, login);
        return new AccountView(account);
    }

    private boolean checkPasswords(String password, Account account) {
        return Objects.equals(account.getPassword(), password);
    }

    private void storeToken(Account account, String login) {
        String token = tokenService.generateToken(login);
        account.setToken(token);
        accountRepository.save(account);
    }
}
