package org.university.innopolis.server.wrappers;

import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;

import javax.validation.constraints.NotNull;

public abstract class RecordWrapper {
    private String description;
    @NotNull
    private double amount;
    @NotNull
    private Currency currency;
    @NotNull
    private long date;

    public RecordWrapper() {
    }

    public abstract Type getType();

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getDate() {
        return date;
    }
}
