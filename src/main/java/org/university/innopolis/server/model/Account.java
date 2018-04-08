package org.university.innopolis.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
    public Account() {
    }

    @Id
    private int id;

    public int getId() {
        return id;
    }
}
