package org.university.innopolis.server.services;

import org.springframework.stereotype.Service;

@Service
public class ReverseToken implements TokenService {
    public ReverseToken() {
    }

    public String generateToken(String login) {
        return new StringBuilder(login).reverse().toString();
    }
}
