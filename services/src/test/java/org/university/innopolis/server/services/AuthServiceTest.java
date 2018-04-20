package org.university.innopolis.server.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.university.innopolis.server.ServiceConf;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.realization.AuthService;
import org.university.innopolis.server.services.realization.helpers.ShaHashEncoder;
import org.university.innopolis.server.views.AccountView;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@EnableJpaRepositories(basePackages = "org.university.innopolis.server")
@EntityScan(basePackages = "org.university.innopolis.server")
@ContextConfiguration(classes = {ServiceConf.class})
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    ShaHashEncoder shaHashEncoder;

    @Autowired
    private EntityManager entityManager;

    private static String password = "password";
    private static String login = "login";
    private static int id = -1;
    private static AccountView view;

    @Test
    public void testSuite() throws Exception {
        test00();
        try {
            test01();
            fail();
        } catch (DuplicatedUserException ignored) {
        }

        test02();

        try {
            test03();
            fail();
        } catch (BadCredentialsException ignored) {
        }

        try {
            test04();
            fail();
        } catch (BadCredentialsException ignored) {
        }

        test05();
        test06();
        test07();
        test08();
        test09();
        test10();
    }

    public void test00() throws DuplicatedUserException, BadCredentialsException {
        view = authService.registerAccount(login, password);
        assertEquals(login, view.getLogin());
        assertNull(view.getToken());
    }

    public void test01() throws DuplicatedUserException, BadCredentialsException {
        authService.registerAccount(login, "password2");
    }

    public void test02() throws BadCredentialsException {
        AccountView res = authService.getAuthentication(login, password);
        assertEquals(login, res.getLogin());
        assertNotNull(res.getToken());
        assertNotEquals(-1, authService.getAccountId(res.getToken()));
        id = authService.getAccountId(res.getToken());
        view = res;
    }

    public void test03() throws BadCredentialsException {
        authService.getAuthentication("notlogin", password);
    }

    public void test04() throws BadCredentialsException {
        authService.getAuthentication(login, "notpassword");
    }

    public void test05() throws BadCredentialsException {
        AccountView res = authService.getAuthentication(login, password);
        assertEquals(view.getLogin(), res.getLogin());
        assertEquals(view.getToken(), res.getToken());
    }

    public void test06() {
        assertNotEquals(-1, id);
        assertEquals(id, authService.getAccountId(view.getToken()));
        authService.revokeToken(view.getToken());
        entityManager.clear();
        assertEquals(-1, authService.getAccountId(view.getToken()));
    }

    public void test07() {
        assertTrue(authService.isAuthorized(id, login));
        assertFalse(authService.isAuthorized(id + 1, login));
    }

    public void test08() {
        assertEquals(-1, authService.getAccountId(null));
    }

    public void test09() throws BadCredentialsException, InterruptedException {
        entityManager.clear();
        Thread.sleep(1000);
        AccountView res = authService.getAuthentication(login, password);
        assertNotEquals(view.getToken(), res.getToken());
        id = authService.getAccountId(res.getToken());
        authService.revokeTokenById(id);
        assertEquals(-1, authService.getAccountId(res.getToken()));
    }

    public void test10() {
        authService.revokeTokenById(-1);
    }
}