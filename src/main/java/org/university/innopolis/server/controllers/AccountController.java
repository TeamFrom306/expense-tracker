package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.DuplicatedUserException;
import org.university.innopolis.server.views.AccountView;

@Controller
@RequestMapping(path = "/api")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping(path = "/user")
    ResponseEntity getAccount(@RequestParam String login) {
        AccountView res = accountService.findAccount(login);
        if (res == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/user")
    ResponseEntity createAccount(@RequestParam String login,
                                 @RequestParam String password) {
        try {
            return ResponseEntity.ok(accountService.createAccount(login, password));
        } catch (DuplicatedUserException e) {
            //TODO response
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
