package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.views.AccountView;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AccountController {
    private AccountService accountService;
    private AuthenticationService authService;

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(AccountService accountService, AuthenticationService authService) {
        this.accountService = accountService;
        this.authService = authService;
    }

    @PostMapping(path = "/balance")
    ResponseEntity adjustBalance(@RequestParam double balance,
                                 @RequestAttribute int accountId) {
        logger.debug("/balance, account: {}", accountId);
        return ResponseEntity.ok(accountService.adjustBalance(balance, accountId));
    }

    @GetMapping(path = "/user")
    ResponseEntity getAccount(@RequestParam String login,
                              @RequestAttribute int accountId) {
        String logString = "/user, account: {}, login: {}, status: {}";

        if (!authService.isAuthorized(accountId, login)) {
            logger.debug(logString, accountId, login, HttpStatus.FORBIDDEN);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        AccountView res = accountService.findAccount(login);
        if (res == null) {
            logger.debug(logString, accountId, login, HttpStatus.NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this login is not found");
        }
        logger.debug(logString, accountId, login, HttpStatus.OK);
        return ResponseEntity.ok(res);
    }
}
