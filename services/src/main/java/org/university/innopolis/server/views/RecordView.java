package org.university.innopolis.server.views;

import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;

import java.util.Date;

public class RecordView {
    private final String description;
    private final double amount;
    private final Currency currency;
    private final Date date;
    private final Type type;

    public RecordView(Record record) {
        description = record.getDescription();
        amount = record.getAmount();
        currency = record.getCurrency();
        date = record.getDate();
        type = record.getType();
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
