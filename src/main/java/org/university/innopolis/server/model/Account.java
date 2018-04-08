package org.university.innopolis.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    private int id;

    private List<Record> records = new ArrayList<>();

    public Account() {
        // Empty for JPA
    }

    public int getId() {
        return id;
    }

    public List<Record> getRecords() {
        return records;
    }
}
