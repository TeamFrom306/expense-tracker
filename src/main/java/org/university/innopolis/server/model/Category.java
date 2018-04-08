package org.university.innopolis.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(generator="categoryIncrement")
    @GenericGenerator(name="categoryIncrement", strategy="increment")
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
