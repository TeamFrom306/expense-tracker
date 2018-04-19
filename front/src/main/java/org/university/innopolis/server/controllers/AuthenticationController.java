package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.views.AccountView;

@Controller
@RequestMapping(path = "/api")
public class AuthenticationController {
    private AuthenticationService authService;
    private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    ResponseEntity login(@RequestParam String login,
                         @RequestParam String password) {
        String logString = "/login, login: {}, password: {}, status: {}";
        try {
            AccountView account = authService.getAuthentication(login, password);
            logger.debug(logString, login, password, HttpStatus.OK);
            return ResponseEntity.ok(account);
        } catch (BadCredentialsException ignored) {
            logger.debug(logString, login, password, HttpStatus.UNAUTHORIZED);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @PostMapping(path = "/register")
    ResponseEntity createAccount(@RequestParam String login,
                                 @RequestParam String password) {
        String logString = "/register, login: {}, password: {}, status: {}";
        try {
            AccountView account = authService.registerAccount(login, password);
            logger.debug(logString, login, password, HttpStatus.OK);
            return ResponseEntity.ok(account);
        } catch (DuplicatedUserException ignored) {
            logger.debug(logString, login, password, HttpStatus.CONFLICT);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This login is already taken");
        } catch (BadCredentialsException e) {
            logger.debug(logString, login, password, HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid login or password");
        }
    }

    @PostMapping(path = "/logout")
    ResponseEntity logout(@RequestAttribute int accountId) {
        String logString = "/logout, account: {}";
        authService.revokeTokenById(accountId);
        logger.debug(logString, accountId);
        return ResponseEntity.ok().build();
    }
}
