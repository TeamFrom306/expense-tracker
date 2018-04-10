package org.university.innopolis.server.services.exceptions;

public class WrongDateParameterException extends Exception {
    public WrongDateParameterException(String date) {
        super(date);
    }
}
