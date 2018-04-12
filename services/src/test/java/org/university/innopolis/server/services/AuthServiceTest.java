package org.university.innopolis.server.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.university.innopolis.server.ServicesConf;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.helpers.ShaHashEncoder;
import org.university.innopolis.server.views.AccountView;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServicesConf.class})
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    ShaHashEncoder shaHashEncoder;

    private static String password = "password";
    private static String login = "login";
    private static int id = -1;
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
        assertNotEquals(-1, authService.getAccountId(res.getToken()));
        id = authService.getAccountId(res.getToken());
        view = res;
    }

    @Test(expected = BadCredentialsException.class)
    public void test03() throws BadCredentialsException {
        authService.getAuthentication("notlogin", password);
    }

    @Test(expected = BadCredentialsException.class)
    public void test04() throws BadCredentialsException {
        authService.getAuthentication(login, "notpassword");
    }

    @Test
    public void test05() throws BadCredentialsException {
        AccountView res = authService.getAuthentication(login, password);
        assertEquals(view.getLogin(), res.getLogin());
        assertEquals(view.getToken(), res.getToken());
    }

    @Test
    public void test06() {
        assertNotEquals(-1, id);
        assertEquals(id, authService.getAccountId(view.getToken()));
        authService.revokeToken(view.getToken());
        assertEquals(-1, authService.getAccountId(view.getToken()));
    }

    @Test
    public void test07() {
        assertTrue(authService.isAuthorized(id, login));
        assertFalse(authService.isAuthorized(id + 1, login));
    }

    @Test
    public void test08() {
        assertEquals(-1, authService.getAccountId(null));
    }

    @Test
    public void test09() throws BadCredentialsException, InterruptedException {
        Thread.sleep(1000);
        AccountView res = authService.getAuthentication(login, password);
        assertNotEquals(view.getToken(), res.getToken());
        id = authService.getAccountId(res.getToken());
        authService.revokeTokenById(id);
        assertEquals(-1, authService.getAccountId(res.getToken()));
    }

    @Test
    public void test10() {
        authService.revokeTokenById(-1);
    }
}