package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.wrappers.AccountWrapper;
import org.university.innopolis.server.wrappers.BalanceWrapper;

import javax.validation.Valid;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(HolderController.class);
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/balance")
    ResponseEntity adjustBalance(@Valid @RequestBody BalanceWrapper wrapper,
                                 @RequestAttribute int holderId,
                                 @RequestBody int accountId) {
        logger.debug("/balance, holder: {}", holderId);
        return ResponseEntity.ok(accountService.adjustBalance(wrapper.getBalance(), holderId, accountId));
    }

    @PostMapping(path = "/account")
    ResponseEntity createAccount(@RequestAttribute int holderId,
                                 @RequestBody AccountWrapper accountWrapper) {
        logger.debug("/account, holder: {}, accountName: {}", holderId, accountWrapper.getName());
        return ResponseEntity.ok(accountService.createAccount(accountWrapper.getName(), holderId));
    }
}
