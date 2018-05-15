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

    /**
     * Responsible for updating balance without transactions
     * Account should belong to Holder
     *
     * @param wrapper   new balance from body of the request
     * @param holderId  holder which added by AuthFilter
     *                  This method will not work without authorization from AuthFilter
     * @param accountId which balance will be changed
     *
     * @return AccountView as a Json
     */
    @PostMapping(path = "/balance")
    ResponseEntity adjustBalance(@Valid @RequestBody BalanceWrapper wrapper,
                                 @RequestAttribute int holderId,
                                 @RequestParam int accountId) {
        logger.debug("/balance, holder: {}", holderId);
        return ResponseEntity.ok(accountService.adjustBalance(wrapper.getBalance(), holderId, accountId));
    }

    /**
     * Creating a new account. Account will be created with given name
     * @param holderId          holder which added by AuthFilter
     *                          This method will not work without authorization from AuthFilter
     * @param accountWrapper    Account name in wrapper
     *
     * @return  AccountView as a Json
     */
    @PostMapping(path = "/account")
    ResponseEntity createAccount(@RequestAttribute int holderId,
                                 @RequestBody AccountWrapper accountWrapper) {
        logger.debug("/account, holder: {}, accountName: {}", holderId, accountWrapper.getName());
        return ResponseEntity.ok(accountService.createAccount(accountWrapper.getName(), holderId));
    }
}
