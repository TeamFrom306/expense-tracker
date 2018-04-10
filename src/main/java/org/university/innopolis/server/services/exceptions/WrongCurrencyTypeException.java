package org.university.innopolis.server.services.exceptions;

import org.university.innopolis.server.common.Currency;

public class WrongCurrencyTypeException extends Exception {
    public WrongCurrencyTypeException(Currency currency) {
        super(currency.toString());
    }
}
