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
    private String password;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    protected Account() {
        // Empty for JPA
    }

    public Account(String login, String password){
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
