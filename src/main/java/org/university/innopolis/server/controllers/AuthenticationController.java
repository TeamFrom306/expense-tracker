package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.*;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.helpers.EncoderService;
import org.university.innopolis.server.views.AccountView;

@Controller
@RequestMapping(path = "/api")
public class AuthenticationController {
    private AuthenticationService authService;
    private AccountService accountService;
    private EncoderService shaEncoder;

    @Autowired
    public AuthenticationController(AuthenticationService authService,
                                    AccountService accountService,
                                    EncoderService shaEncoder) {
        this.authService = authService;
        this.accountService = accountService;
        this.shaEncoder = shaEncoder;
    }

    @PostMapping(path = "/login")
    ResponseEntity login(@RequestParam String login,
                         @RequestParam String password) {
        try {
            AccountView account = authService.getAuthAccount(login, shaEncoder.getHash(password));
            return ResponseEntity.ok(account);
        } catch (BadCredentialsException ignored) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @PostMapping(path = "/register")
    ResponseEntity createAccount(@RequestParam String login,
                                 @RequestParam String password) {
        try {
            return ResponseEntity.ok(accountService.createAccount(login, shaEncoder.getHash(password)));
        } catch (DuplicatedUserException ignored) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This login is already taken");
        }
    }
}
