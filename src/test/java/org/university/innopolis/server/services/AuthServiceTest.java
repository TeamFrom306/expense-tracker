package org.university.innopolis.server.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.university.innopolis.server.AppConfig;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.helpers.ShaHashEncoder;
import org.university.innopolis.server.views.AccountView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    ShaHashEncoder shaHashEncoder;

    private static String password = "password";
    private static String login = "login";
    private static AccountView view;

    @Test
    public void test00() throws DuplicatedUserException, BadCredentialsException {
        view = authService.registerAccount(login, password);
        assertEquals(login, view.getLogin());
        assertNull(view.getToken());
    }

    @Test(expected = DuplicatedUserException.class)
    public void test01() throws DuplicatedUserException, BadCredentialsException {
        authService.registerAccount(login, "password2");
    }

    @Test
    public void test02() throws BadCredentialsException {
        AccountView res = authService.getAuthentication(login, password);
        assertEquals(login, res.getLogin());
        assertNotNull(res.getToken());
        view = res;
    }

//    @Test
//    public void getAccountId() {
//    }
//
//    @Test
//    public void revokeTokenById() {
//    }
//
//    @Test
//    public void isAuthorized() {
//    }
//
//    @Test
//    public void revokeToken() {
//    }
}