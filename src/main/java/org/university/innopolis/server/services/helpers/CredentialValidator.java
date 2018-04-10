package org.university.innopolis.server.services.helpers;

import org.university.innopolis.server.services.exceptions.BadCredentialsException;

import java.util.regex.Pattern;

public class CredentialValidator {
    private static Pattern loginPattern = Pattern
            .compile("^([a-zA-Z])([_\\-a-zA-Z0-9]){1,18}([a-zA-Z0-9])$");

    private static Pattern passwordPattern = Pattern
            .compile("^([_\\-!@#$%^&*()+=<>,.\"'{}\\[\\]/?~`;:a-zA-Z0-9]{6,25})");

    static boolean isValidLogin(String s) {
        return loginPattern.matcher(s).matches();
    }

    static boolean isValidPassword(String s) {
        return passwordPattern.matcher(s).matches();
    }

    static void validatePassword(String password) throws BadCredentialsException {
        if (password == null ||
                password.isEmpty() ||
                !CredentialValidator.isValidPassword(password))
            throw new BadCredentialsException();
    }

    static void validateLogin(String login) throws BadCredentialsException {
        if (login == null ||
                login.isEmpty() ||
                !CredentialValidator.isValidLogin(login))
            throw new BadCredentialsException();
    }

    public static void validateCredentials(String login, String password) throws BadCredentialsException {
        validatePassword(password);
        validateLogin(login);
    }
}
