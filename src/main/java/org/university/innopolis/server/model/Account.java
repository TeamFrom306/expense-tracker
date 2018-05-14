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

    private String name;
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    private Holder holder;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    protected Account() {
        // For JPA
    }

    public Account(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int getId() {
        return id;
    }
}
