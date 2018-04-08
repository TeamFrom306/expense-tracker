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

    private String login;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    protected Account() {
        // Empty for JPA
    }

    public Account(String login){
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public List<Record> getRecords() {
        return records;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getLogin() {
        return login;
    }
}
