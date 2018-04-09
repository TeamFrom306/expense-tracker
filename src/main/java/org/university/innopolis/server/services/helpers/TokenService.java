package org.university.innopolis.server.services.helpers;

public interface TokenService {
    String generateToken(String login, int id);

    int getAccountId(String token);
}
