package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.university.innopolis.server.services.AccountService;

@Controller
@RequestMapping(path = "/api")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping(path = "/user")
    ResponseEntity<?> getAccount(@RequestParam Integer id) {
        if (id != null) {
            Object res = accountService.getAccountById(id);
            if (res == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/user")
    ResponseEntity<?> createAccount() {
        accountService.createAccount();
        return ResponseEntity.accepted().build();
    }
}
