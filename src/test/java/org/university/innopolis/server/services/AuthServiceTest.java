package org.university.innopolis.server.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.university.innopolis.server.TestConf;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.realization.AuthService;
import org.university.innopolis.server.services.realization.helpers.ShaHashEncoder;
import org.university.innopolis.server.views.HolderView;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource({"classpath:application-test.properties"})
@ContextConfiguration(classes = {TestConf.class})
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
    private static HolderView view;

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
        view = authService.registerHolder(login, password);
        assertEquals(login, view.getLogin());
        assertNull(view.getToken());
    }

    public void test01() throws DuplicatedUserException, BadCredentialsException {
        authService.registerHolder(login, "password2");
    }

    public void test02() throws BadCredentialsException {
        HolderView res = authService.getAuthentication(login, password);
        assertEquals(login, res.getLogin());
        assertNotNull(res.getToken());
        assertNotEquals(-1, authService.getHolderId(res.getToken()));
        id = authService.getHolderId(res.getToken());
        view = res;
    }

    public void test03() throws BadCredentialsException {
        authService.getAuthentication("notlogin", password);
    }

    public void test04() throws BadCredentialsException {
        authService.getAuthentication(login, "notpassword");
    }

    public void test05() throws BadCredentialsException {
        HolderView res = authService.getAuthentication(login, password);
        assertEquals(view.getLogin(), res.getLogin());
        assertEquals(view.getToken(), res.getToken());
    }

    public void test06() {
        assertNotEquals(-1, id);
        assertEquals(id, authService.getHolderId(view.getToken()));
        authService.revokeToken(view.getToken());
        entityManager.clear();
        assertEquals(-1, authService.getHolderId(view.getToken()));
    }

    public void test07() {
        assertTrue(authService.isAuthorized(id, login));
        assertFalse(authService.isAuthorized(id + 1, login));
    }

    public void test08() {
        assertEquals(-1, authService.getHolderId(null));
    }

    public void test09() throws BadCredentialsException, InterruptedException {
        entityManager.clear();
        Thread.sleep(1000);
        HolderView res = authService.getAuthentication(login, password);
        assertNotEquals(view.getToken(), res.getToken());
        id = authService.getHolderId(res.getToken());
        authService.revokeTokenById(id);
        assertEquals(-1, authService.getHolderId(res.getToken()));
    }

    public void test10() {
        authService.revokeTokenById(-1);
    }
}