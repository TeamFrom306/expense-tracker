package org.university.innopolis.server.services.exceptions;

public class WrongDateParameterException extends Exception {
    public WrongDateParameterException(long date) {
        super(String.valueOf(date));
    }
}
