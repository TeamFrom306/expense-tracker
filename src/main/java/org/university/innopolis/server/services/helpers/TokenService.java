package org.university.innopolis.server.services.helpers;

import org.university.innopolis.server.services.exceptions.CorruptedTokenException;
import org.university.innopolis.server.services.exceptions.ExpiredTokenException;

public interface TokenService {
    String generateToken(String login, int id);

    void validateToken(String token) throws ExpiredTokenException, CorruptedTokenException;
}
