package org.university.innopolis.server.kafka.common;

import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;

import java.io.Serializable;
import java.util.Date;

public class KafkaRecord implements Serializable {

    private String description;
    private double amount;
    private Currency currency;
    private Date date;
    private Type type;

    public KafkaRecord() {
    }

    public KafkaRecord(String description, double amount, Currency currency, Date date, Type type) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.type = type;
    }

    @Override
    public String toString() {
        return "KafkaRecord{" +
                "amount=" + amount +
                ", type=" + type +
                '}';
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getDate() {
        return date;
    }
}
