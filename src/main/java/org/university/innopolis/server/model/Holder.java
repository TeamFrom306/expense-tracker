package org.university.innopolis.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Holder {
    @Id
    @GeneratedValue(generator="holderIncrement")
    @GenericGenerator(name="holderIncrement", strategy="increment")
    private int id;

    private String login;
    private String password;
    private String token;

    @OneToMany(mappedBy = "holder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "holder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    protected Holder() {
        // Empty for JPA
    }

    public Holder(String login, String password){
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
