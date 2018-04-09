package org.university.innopolis.server.services;

import org.university.innopolis.server.views.AccountView;

public interface AuthenticationService {
    boolean isAuthorized(String token);

    AccountView getAuthAccount(String login, String password) throws BadCredentialsException;
}
