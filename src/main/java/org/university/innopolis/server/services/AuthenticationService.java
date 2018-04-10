package org.university.innopolis.server.services;

import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.views.AccountView;

public interface AuthenticationService {
    AccountView getAuthAccount(String login, String password) throws BadCredentialsException;

    int getAccountId(String token);

    void revokeTokenById(int id);
}
