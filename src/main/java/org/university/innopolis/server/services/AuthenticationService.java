package org.university.innopolis.server.services;

import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.views.AccountView;

public interface AuthenticationService {
    AccountView getAuthentication(String login, String password) throws BadCredentialsException;

    int getAccountId(String token);

    void revokeTokenById(int id);

    AccountView registerAccount(String login, String password) throws DuplicatedUserException, BadCredentialsException;

    boolean isAuthorized(int accountId, String login);
}
