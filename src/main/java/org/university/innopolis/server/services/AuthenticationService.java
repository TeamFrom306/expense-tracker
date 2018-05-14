package org.university.innopolis.server.services;

import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.views.HolderView;

public interface AuthenticationService {
    HolderView getAuthentication(String login, String password) throws BadCredentialsException;

    int getHolderId(String token);

    void revokeTokenById(int id);

    HolderView registerHolder(String login, String password) throws DuplicatedUserException, BadCredentialsException;

    boolean isAuthorized(int holderId, String login);

    void revokeToken(String token);
}
