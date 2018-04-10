package org.university.innopolis.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Record {
    @Id
    @GeneratedValue(generator="recordIncrement")
    @GenericGenerator(name="recordIncrement", strategy="increment")
    private int id;
    private String description;
    private double amount;
    private Currency currency;
    private Date date;
    private Type type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    protected Record() {
    }

    public Record(double amount, Currency currency, Date date, Type type) {
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.type = type;
    }
    public Record(double amount, Currency currency, Date date, String description, Type type) {
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.description = description;
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Category getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
