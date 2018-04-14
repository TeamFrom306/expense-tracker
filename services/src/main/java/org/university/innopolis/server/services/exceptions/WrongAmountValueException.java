package org.university.innopolis.server.services.exceptions;

public class WrongAmountValueException extends Exception {
    public WrongAmountValueException(double amount) {
        super(Double.toString(amount));
    }
}
