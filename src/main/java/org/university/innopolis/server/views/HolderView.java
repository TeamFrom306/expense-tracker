package org.university.innopolis.server.views;

import java.io.Serializable;

public class HolderView implements Serializable {
    private final String login;
    private final String token;

    public HolderView(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }
}
