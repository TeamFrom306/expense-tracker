package org.university.innopolis.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Record {
    @Id
    private int id;
    private String description;
    private int amount;
    private Currency currency;
    private Date date;

    protected Record() {
    }

    public Record(int amount, Currency currency, Date date) {
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }
    public Record(int amount, Currency currency, Date date, String description) {
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
}
