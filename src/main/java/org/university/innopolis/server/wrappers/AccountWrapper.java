package org.university.innopolis.server.wrappers;

import javax.validation.constraints.NotNull;

public class AccountWrapper {
    @NotNull
    private String name;

    AccountWrapper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
