package org.university.innopolis.server.services.helpers;

import org.junit.Test;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;

public class CredentialValidatorTest {

    @Test(expected = BadCredentialsException.class)
    public void validatePassword00() throws BadCredentialsException {
        CredentialValidator.validatePassword("12");
    }

    @Test(expected = BadCredentialsException.class)
    public void validatePassword01() throws BadCredentialsException {
        CredentialValidator.validatePassword("12345678901234567890123456");
    }

    @Test
    public void validatePassword02() throws BadCredentialsException {
        CredentialValidator.validatePassword("123456");
    }

    @Test(expected = BadCredentialsException.class)
    public void validateLogin00() throws BadCredentialsException {
        CredentialValidator.validateLogin("12");
    }

    @Test
    public void validateLogin01() throws BadCredentialsException {
        CredentialValidator.validateLogin("a12");
    }

    @Test(expected = BadCredentialsException.class)
    public void validateLogin02() throws BadCredentialsException {
        CredentialValidator.validateLogin("a");
    }

    @Test(expected = BadCredentialsException.class)
    public void validateLogin03() throws BadCredentialsException {
        CredentialValidator.validateLogin("a23456789012345678901");
    }

    @Test
    public void validateLogin04() throws BadCredentialsException {
        CredentialValidator.validateLogin("a2345678901234567890");
    }
}