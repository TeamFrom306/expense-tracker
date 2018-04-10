package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.helpers.CredentialValidator;
import org.university.innopolis.server.services.helpers.EncoderService;
import org.university.innopolis.server.services.helpers.TokenService;
import org.university.innopolis.server.views.AccountView;

import java.util.Objects;

@Component
public class AuthService implements AuthenticationService {
    private AccountRepository accountRepository;
    private TokenService tokenService;
    private EncoderService shaEncoder;

    @Autowired
    public AuthService(AccountRepository accountRepository,
                       TokenService tokenService,
                       EncoderService shaEncoder) {
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
        this.shaEncoder = shaEncoder;
    }

    @Override
    public int getAccountId(String token) {
        if (token == null)
            return -1;
        Account account = accountRepository.getByToken(token);
        return account == null ? -1 : account.getId();
    }

    @Override
    public void revokeTokenById(int id) {
        Account account = accountRepository.getById(id);
        if (account != null) {
            account.setToken(null);
            accountRepository.save(account);
        }
    }


    @Override
    public AccountView registerAccount(String login, String password) throws
            DuplicatedUserException,
            BadCredentialsException {
        CredentialValidator.validateCredentials(login, password);

        final String encodedPassword = shaEncoder.getHash(password);

        if (containsLogin(login))
            throw new DuplicatedUserException(login);

        Account res = accountRepository.save(new Account(login, encodedPassword));
        return new AccountView(res);
    }

    @Override
    public AccountView getAuthentication(String login, String password) throws BadCredentialsException {
        CredentialValidator.validateCredentials(login, password);

        final String encodedPassword = shaEncoder.getHash(password);

        Account account = accountRepository.getByLogin(login);
        if (account == null || !checkPasswords(encodedPassword, account))
            throw new BadCredentialsException();
        if (account.getToken() == null)
            storeToken(account, login);
        return new AccountView(account);
    }

    @Override
    public boolean isAuthorized(int accountId, String login) {
        Account account = accountRepository.getByIdAndLogin(accountId, login);
        return account != null;
    }

    private boolean containsLogin(String login) {
        return accountRepository.getByLogin(login) != null;
    }

    private boolean checkPasswords(String password, Account account) {
        return Objects.equals(account.getPassword(), password);
    }

    private void storeToken(Account account, String login) {
        String token = tokenService.generateToken(login, account.getId());
        account.setToken(token);
        accountRepository.save(account);
    }
}
