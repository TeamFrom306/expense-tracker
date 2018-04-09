package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.views.AccountView;

import java.util.Objects;

@Component
public class AuthService implements AuthenticationService {
    private AccountRepository accountRepository;
    private TokenService tokenService;

    @Autowired
    public AuthService(AccountRepository accountRepository, TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
    }

    @Override
    public boolean isAuthorized(String token) {
        return accountRepository.getByToken(token) != null;
    }

    @Override
    public AccountView getAuthAccount(String login, String password) throws BadCredentialsException {
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
