package org.university.innopolis.server.services.helpers;

import org.junit.Test;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;

public class CredentialValidatorTest {
    @Test(expected = BadCredentialsException.class)
    public void validateCredentials00() throws BadCredentialsException {
        String password = "12";
        String login = "valid";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials01() throws BadCredentialsException {
        String password = "12345678901234567890123456";
        String login = "valid";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test
    public void validateCredentials02() throws BadCredentialsException {
        String password = "123456";
        String login = "valid";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials03() throws BadCredentialsException {
        String password = "valid_password";
        String login = "12";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test
    public void validateCredentials04() throws BadCredentialsException {
        String password = "valid_password";
        String login = "a12";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials05() throws BadCredentialsException {
        String password = "valid_password";
        String login = "a";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials06() throws BadCredentialsException {
        String password = "valid_password";
        String login = "a23456789012345678901";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test
    public void validateCredentials07() throws BadCredentialsException {
        String password = "123456";
        String login = "a2345678901234567890";
        CredentialValidator.validateCredentials(login, password);
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials08() throws BadCredentialsException {
        String login = "a23456789012345678901";
        CredentialValidator.validateCredentials(login, null);
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials09() throws BadCredentialsException {
        CredentialValidator.validateCredentials(null, "password");
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials10() throws BadCredentialsException {
        CredentialValidator.validateCredentials("", "password");
    }

    @Test(expected = BadCredentialsException.class)
    public void validateCredentials11() throws BadCredentialsException {
        CredentialValidator.validateCredentials("login", "");
    }
}