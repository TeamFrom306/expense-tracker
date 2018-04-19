package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.views.AccountView;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AuthenticationController {
    private AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    ResponseEntity login(@RequestParam String login,
                         @RequestParam String password) {
        try {
            AccountView account = authService.getAuthentication(login, password);
            return ResponseEntity.ok(account);
        } catch (BadCredentialsException ignored) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @PostMapping(path = "/register")
    ResponseEntity createAccount(@RequestParam String login,
                                 @RequestParam String password) {
        try {
            return ResponseEntity.ok(authService.registerAccount(login, password));
        } catch (DuplicatedUserException ignored) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This login is already taken");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid login or password");
        }
    }

    @PostMapping(path = "/logout")
    ResponseEntity logout(@RequestAttribute int accountId) {
        authService.revokeTokenById(accountId);
        return ResponseEntity.ok().build();
    }
}
