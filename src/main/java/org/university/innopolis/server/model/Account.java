package org.university.innopolis.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(generator="accountIncrement")
    @GenericGenerator(name="accountIncrement", strategy="increment")
    private int id;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
